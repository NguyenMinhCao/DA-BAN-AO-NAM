package vn.duantn.sominamshop.service;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.OrderHistory;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.Promotion;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;

import vn.duantn.sominamshop.model.constants.ShippingMethod;
import vn.duantn.sominamshop.model.dto.AddressDTO;
import vn.duantn.sominamshop.model.dto.OrderDTO;
import vn.duantn.sominamshop.model.dto.OrderUpdateRequestDTO;
import vn.duantn.sominamshop.model.dto.request.DataUpdateOrderDetailDTO;
import vn.duantn.sominamshop.repository.CartRepository;
import vn.duantn.sominamshop.repository.OrderDetailRepository;
import vn.duantn.sominamshop.repository.OrderRepository;
import vn.duantn.sominamshop.util.SecurityUtil;

@Service
public class OrderService {
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PromotionService promotionService;
    private final AddressService addressService;
    private final OrderHistoryService orderHistoryService;

    public OrderService(ProductService productService, UserService userService, CartRepository cartRepository,
            CartService cartService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            PromotionService promotionService, AddressService addressService,
            OrderHistoryService orderHistoryService) {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.promotionService = promotionService;
        this.addressService = addressService;
        this.orderHistoryService = orderHistoryService;
    }

    public List<Order> findOrderByUser(User user) {
        return this.orderRepository.findOrderByUser(user);
    }

    public void orderCheckout(HttpSession session) {
        String emailUser = (String) session.getAttribute("email");
        User userByEmail = this.userService.findUserByEmail(emailUser);
        if (userByEmail != null) {
            Cart cartByUser = this.cartService.findCartByUser(userByEmail);
            if (cartByUser != null) {
                List<OrderDetail> lstOrderDetails = new ArrayList<>();
                Order order = this.findOrderByStatusAndCreatedBy();
                // save order
                if (order.getTotalAmount() == null) {
                    double shippingPrice = 0;
                    double totalPrice = (double) session.getAttribute("totalPrice");

                    if (order.getShippingMethod().equals("EXPRESS")) {
                        shippingPrice = 50000;
                    } else if (order.getShippingMethod().equals("FAST")) {
                        shippingPrice = 30000;
                    } else {
                        shippingPrice = 20000;
                    }
                    order.setTotalAmount(BigDecimal.valueOf(totalPrice + shippingPrice));
                }
                order.setUser(userByEmail);
                order.setTotalProducts(cartByUser.getTotalProducts());
                this.orderRepository.save(order);

                // create order Detail
                List<CartDetail> lstCartDetail = cartByUser.getCartDetail();

                for (CartDetail cartDetail : lstCartDetail) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
//                    orderDetail.setPrice(cartDetail.getQuantity() * cartDetail.getProduct().getPrice());
                    orderDetail.setProductDetail(cartDetail.getProductDetail());
                    orderDetail.setQuantity(cartDetail.getQuantity());
                    // save order detail
                    this.orderDetailRepository.save(orderDetail);

                    lstOrderDetails.add(orderDetail);
                    // delete cartDetail
                    this.cartService.deleteCartDetail(cartDetail);
                    session.setAttribute("sum", 0);
                }

                // update order
                order.setOrderDetails(lstOrderDetails);
                order.setDeliveryStatus(DeliveryStatus.PENDING);
                if (order.getPaymentMethod().toString().equalsIgnoreCase("COD")) {
                    order.setPaymentStatus(PaymentStatus.PENDING);
                } else {
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                }
                order.setOrderSource(true);
                this.orderRepository.save(order);

                // delete cart
                this.cartService.deleteCartByUser(userByEmail);
                // save history order
                this.orderHistoryService.orderCheckoutHistory(order);
            }
        }
    }

    public Map<String, Object> orderCheckoutUpdate(OrderUpdateRequestDTO orderReq, HttpSession session) {

        session.removeAttribute("totalPayment");
        //
        Long promotionId = orderReq.getPromotionId();
        Long addressId = orderReq.getAddressId();
        String shippingMethod = orderReq.getShippingMethod();
        String paymentMethod = orderReq.getPaymentMethod();

        Map<String, Object> response = new HashMap<>();

        Optional<Promotion> promotionById = null;
        Order order = this.findOrderByStatusAndCreatedBy();

        if (order != null) {
            if (addressId != null) {
                Address addressById = this.addressService.findAddressById(addressId);
                order.setAddress(addressById);
                AddressDTO dto = new AddressDTO();
                dto.setAddress(addressById.getAddress());
                dto.setFullName(addressById.getFullName());
                dto.setPhoneNumber(addressById.getPhoneNumber());
                dto.setStreetDetails(addressById.getStreetDetails());
                dto.setStatus(addressById.isStatus());
                session.setAttribute("isChangeAddress", "true");
                response.put("addressById", dto);
            }

            if (paymentMethod != null) {
                order.setPaymentMethod(paymentMethod);
            }

            if (promotionId != null) {
                promotionById = this.promotionService.findPromotionById(promotionId);
                if (promotionById.isPresent()) {
                    order.setPromotion(promotionById.get());
                    session.setAttribute("promotionInOrder", order.getPromotion());
                }
            }

            if (shippingMethod != null) {
                if (shippingMethod.equals("EXPRESS")) {
                    order.setShippingMethod(ShippingMethod.EXPRESS);
                } else if (shippingMethod.equals("FAST")) {
                    order.setShippingMethod(ShippingMethod.FAST);
                } else {
                    order.setShippingMethod(ShippingMethod.SAVE);
                }
            }

            this.orderRepository.save(order);

            if (shippingMethod != null || promotionId != null) {
                String emailUser = (String) session.getAttribute("email");

                // Lấy ra tổng tiền hàng
                List<CartDetail> lstCartDetail = this.cartService.getAllCartDetailByCart(emailUser);
                double totalPrice = 0;
                for (CartDetail cartDetail : lstCartDetail) {
                    totalPrice += cartDetail.getPrice();
                }

                double shippingPrice = 0;
                double discountValue = 0;

                String shippingMethodString = order.getShippingMethod().toString();

                if (shippingMethodString.equals("EXPRESS")) {
                    shippingPrice = 50000;
                } else if (shippingMethodString.equals("FAST")) {
                    shippingPrice = 30000;
                } else {
                    shippingPrice = 20000;
                }

                double totalPayment = 0;
                totalPayment = totalPrice + shippingPrice;

                // người dùng truyền lên promotion
                if (promotionId != null) {
                    discountValue = Double.parseDouble(promotionById.get().getDiscountValue());
                }
                // người dùng không truyền lên nhưng trong DB có
                else if (promotionId == null && order.getPromotion() != null) {
                    discountValue = Double.parseDouble(order.getPromotion().getDiscountValue());
                }
                // người dùng không truyền lên trong DB cũng ko có
                else {
                    discountValue = 0;
                }

                totalPayment = totalPayment - discountValue;

                order.setTotalAmount(BigDecimal.valueOf(totalPayment));
                this.orderRepository.save(order);

                session.setAttribute("shippingMethodInOrder", order.getShippingMethod());
                session.setAttribute("totalPayment", totalPayment);
                // session.setAttribute("paymentMethodInOrder", order.getPaymentMethod());

                // Trả về dữ liệu cần thiết cho client
                // response.put("shippingMethod", order.getShippingMethod());

                response.put("totalPayment", totalPayment);
                response.put("shippingPrice", shippingPrice);
                if (discountValue != 0) {
                    response.put("discountValue", order.getPromotion().getDiscountValue());
                }
            }

        }
        return response;
    }

    public Optional<Order> findOrderById(Long id) {
        return this.orderRepository.findById(id);
    }

    public Optional<OrderDetail> findOrderDetailById(Long id) {
        return this.orderDetailRepository.findById(id);
    }

    public void saveOrder(Order order) {
        this.orderRepository.save(order);
    }

    public void deleteOrder(Order order) {
        this.orderRepository.delete(order);
    }

    public Order findOrderByStatusAndCreatedBy() {
        String createdBy = SecurityUtil.getCurrentUserLogin().get();
        return this.orderRepository.findOrderByDeliveryStatusAndCreatedBy(createdBy);
    }

    public List<Order> getAllOrdersByDeliveryStatusNotNull() {
        return this.orderRepository.findAllOrderByDeliveryStatusNotNull();
    }

    public void updateOrderDetail(DataUpdateOrderDetailDTO dto, String idOrderD) {
        Optional<OrderDetail> findOrderDetailById = this.findOrderDetailById(Long.valueOf(idOrderD));
        if (findOrderDetailById.isPresent()) {
            OrderDetail orderUnwrap = findOrderDetailById.get();
            if (dto.isUpdateORemove()) {
                Product productById = this.productService.findProductById(dto.getProductId());
                orderUnwrap.setQuantity(dto.getQuantity());
                if (productById != null) {
                    // Double newPrice = productById.getPrice() * dto.getQuantity();
                    Double newPrice = 23.3 * dto.getQuantity();
                    orderUnwrap.setPrice(newPrice);
                }
                this.orderDetailRepository.save(orderUnwrap);

            } else {
                this.orderDetailRepository.delete(orderUnwrap);
            }
        }

    }

    @Transactional
    public OrderDTO saveInvoice(Order order) {
        order.setOrderSource(false);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setDeliveryStatus(DeliveryStatus.COMPLETED);
        Order orderCreate = orderRepository.save(order);
        OrderDTO orderDTO = OrderDTO.toOrderDTO(orderCreate);
        return orderDTO;
    }

    @Transactional
    public List<OrderDetail> saveInvoiceDetail(List<OrderDetail> list) {
        return orderDetailRepository.saveAll(list);
    }

}
