package vn.duantn.sominamshop.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
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
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.OrderStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;

import vn.duantn.sominamshop.model.constants.ShippingMethod;
import vn.duantn.sominamshop.model.dto.AddressDTO;
import vn.duantn.sominamshop.model.dto.OrderDTO;
import vn.duantn.sominamshop.model.dto.OrderDetailDTO;
import vn.duantn.sominamshop.model.dto.OrderUpdateRequestDTO;
import vn.duantn.sominamshop.model.dto.request.DataUpdateOrderDetailDTO;
import vn.duantn.sominamshop.model.dto.response.ResOrderDTO;
import vn.duantn.sominamshop.model.dto.response.ResultPaginationDTO;
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
    private final ProductDetailService productDetailService;

    public OrderService(ProductService productService, UserService userService, CartRepository cartRepository,
            CartService cartService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            PromotionService promotionService, AddressService addressService,
            OrderHistoryService orderHistoryService, ProductDetailService productDetailService) {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.promotionService = promotionService;
        this.addressService = addressService;
        this.orderHistoryService = orderHistoryService;
        this.productDetailService = productDetailService;
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
                    orderDetail.setPrice(cartDetail.getQuantity() * cartDetail.getProductDetail().getPrice());
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

                // set address
                List<Address> addressByUser = this.addressService.findAllAddressByUser(userByEmail);
                for (Address address : addressByUser) {
                    if (address.isStatus()) {
                        order.setAddress(address);
                    }
                }

                // save time create order
                order.setCreatedAt(LocalDateTime.now());

                // save order
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

        Optional<Coupon> promotionById = null;
        Order order = this.findOrderByStatusAndCreatedBy();

        if (order != null) {
            if (addressId != null) {
                Address addressById = this.addressService.findAddressById(addressId);
                order.setAddress(addressById);
                AddressDTO dto = new AddressDTO();
                // dto.setAddress(addressById.getAddress());
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

    public ResultPaginationDTO fetchAllOrders(Specification<Order> spec, Pageable pageable) {
        Page<Order> page = this.orderRepository.findAll(spec, pageable);

        List<Order> lstOrders = page.getContent();

        List<ResOrderDTO> orderRs = convertOrderToOrderSearchResponse(lstOrders);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(page.getSize());
        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getTotalElements());
        mt.setCurrentPageElements(page.getNumberOfElements());
        
        rs.setMeta(mt);
        rs.setResult(orderRs);
        return rs;
    }

    public void saveOrder(Order order) {
        this.orderRepository.save(order);
    }

    public void deleteOrder(Order order) {
        this.orderRepository.delete(order);
    }

    public void deleteOrderDetail(long idOrderDetail, long productDetailId) {
        this.orderDetailRepository.deleteByOrderDetailIdAndProductDetailId(idOrderDetail, productDetailId);
    }

    public Order findOrderByStatusAndCreatedBy() {
        String createdBy = SecurityUtil.getCurrentUserLogin().get();
        return this.orderRepository.findOrderByDeliveryStatusAndCreatedBy(createdBy);
    }

    public List<ResOrderDTO> getAllOrdersByDeliveryStatusNotNull() {
        return convertOrderToOrderSearchResponse(this.orderRepository.findAllOrderByDeliveryStatusNotNull());
    }

    public List<ResOrderDTO> convertOrderToOrderSearchResponse(List<Order> lstOrder) {
        List<ResOrderDTO> orderSRes = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Order order : lstOrder) {
            ResOrderDTO newOrderRes = new ResOrderDTO();
            newOrderRes.setDeliveryStatus(order.getDeliveryStatus());
            newOrderRes.setId(order.getId());
            newOrderRes.setFullName(
                    order.getUser() != null ? order.getUser().getFullName() : "Không tồn tại");
            newOrderRes.setTotalAmount(order.getTotalAmount());
            newOrderRes.setOrderSource(order.getOrderSource() == true ? "Website" : "Tại quầy");
            String formattedDateCreate = order.getCreatedAt().format(formatter);
            newOrderRes.setCreateAt(formattedDateCreate);
            newOrderRes.setOrderStatus(order.getOrderStatus());
            newOrderRes.setPaymentStatus(order.getPaymentStatus());
            orderSRes.add(newOrderRes);
        }

        return orderSRes;
    }

    public void updateOrderDetail(DataUpdateOrderDetailDTO dto, String idOrderD) {
        Optional<OrderDetail> findOrderDetailById = this.findOrderDetailById(Long.valueOf(idOrderD));
        if (findOrderDetailById.isPresent()) {
            OrderDetail orderUnwrap = findOrderDetailById.get();
            if (dto.isUpdateORemove()) {
                Optional<ProductDetail> productDetailById = this.productDetailService
                        .findProductDetailById(dto.getProductDetailId());
                orderUnwrap.setQuantity(dto.getQuantity());
                if (productDetailById.isPresent()) {
                    Double newPrice = productDetailById.get().getPrice() * dto.getQuantity();
                    orderUnwrap.setPrice(newPrice);
                }
                this.orderDetailRepository.save(orderUnwrap);

            } else {
                this.orderDetailRepository.delete(orderUnwrap);
            }
        }

    }

    public List<Order> getOrdersByIdPrefix(String prefix) {
        return orderRepository.findByIdStartingWith(prefix);
    }

    @Transactional
    public OrderDTO saveInvoice(Order order) {
        order.setOrderSource(false);
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setDeliveryStatus(DeliveryStatus.COMPLETED);
        order.setOrderStatus(OrderStatus.PENDING_INVOICE);
        Order orderCreate = orderRepository.save(order);
        OrderDTO orderDTO = OrderDTO.toOrderDTO(orderCreate);
        return orderDTO;
    }
    @Transactional
    public OrderDTO updateInvoice(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);
        if(order == null){
            return null;
        }
        if(orderDTO.getPaymentStatus() == PaymentStatus.COMPLETED){
            order.setPaymentStatus(PaymentStatus.COMPLETED);
        }
        if(orderDTO.getPromotion() !=null){
            order.setPromotion(Coupon.builder().id(orderDTO.getPromotion().getId()).build());
        }
        order.setNote(orderDTO.getNote());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setTotalProducts(orderDTO.getTotalProducts());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        if(orderDTO.getUser() != null){
            if(orderDTO.getUser().getId() == 0){
                order.setUser(null);
            }else{
                order.setUser(User.builder().id(orderDTO.getUser().getId()).build());
            }
        }
        Order orderCreate = orderRepository.save(order);
        OrderDTO orderDTO2 = OrderDTO.toOrderDTO(orderCreate);
        return orderDTO2;
    }

    @Transactional
    public List<OrderDetail> saveInvoiceDetails(List<OrderDetail> list) {
        return orderDetailRepository.saveAll(list);
    }
    @Transactional
    public Map<String, Long> saveInvoiceDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = OrderDetail.builder()
                .order(Order.builder().id(orderDetailDTO.getOrderId()).build())
                .id(orderDetailDTO.getId())
                .productDetail(ProductDetail.builder().id(orderDetailDTO.getProductDetail().getId()).build())
                .price(orderDetailDTO.getPrice())
                .quantity(orderDetailDTO.getQuantity())
                .build();
        OrderDetail orderDetail1 = orderDetailRepository.save(orderDetail);
        Map<String, Long> map = new HashMap<>();
        map.put("id", orderDetail1.getId());
        return map;
    }
    @Transactional
    public void deleteInvoiceDetail(Long id){
        orderDetailRepository.deleteByOrderDetailIdAndProductDetailId(id);
    }
    public List<OrderDTO> getOrderNonPendingAndPos(DeliveryStatus deliveryStatus, PaymentStatus paymentStatus){
        Pageable pageable = PageRequest.of(0,5);
        List<Order> listOrder = orderRepository.getAllOrderNonPendingAndPos(deliveryStatus, paymentStatus, pageable);
        List<OrderDTO> listOrderDTO = listOrder.stream().map(OrderDTO :: toOrderDTO).collect(Collectors.toList());
        return listOrderDTO;
    }

    public List<OrderDetailDTO> getOrderDetailByOrderId(Long id){
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailByOrderId(id);
        orderDetails.forEach(item -> System.out.println(item.getProductDetail().getPrice() + ": giá sản phẩm trong này"));
        List<OrderDetailDTO> orderDetailDTOS = orderDetails.stream().map(OrderDetailDTO :: toOrderDetailDTO).collect(Collectors.toList());
        return orderDetailDTOS;
    }
    public OrderDTO getOrderById(Long id){
        Order order = orderRepository.getAllOrderById(id).orElse(null);
        OrderDTO orderDTO = OrderDTO.toOrderDTO(order);
        return orderDTO;
    }
}
