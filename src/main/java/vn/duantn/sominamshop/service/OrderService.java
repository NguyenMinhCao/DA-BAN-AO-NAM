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
import vn.duantn.sominamshop.model.constants.DiscountType;
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
    private final CouponService promotionService;
    private final AddressService addressService;
    private final OrderHistoryService orderHistoryService;
    private final ProductDetailService productDetailService;

    public OrderService(ProductService productService, UserService userService, CartRepository cartRepository,
            CartService cartService, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            CouponService promotionService, AddressService addressService,
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

    @Transactional
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
                order.setOrderStatus(OrderStatus.PENDING);
                if (order.getPaymentMethod().toString().equalsIgnoreCase("COD")) {
                    order.setPaymentStatus(PaymentStatus.PENDING);
                } else {
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                }
                order.setOrderSource(true);

                // set address
                List<Address> addressByUser = this.addressService.findAllAddressByUser(userByEmail);
                for (Address address : addressByUser) {
                    if (address.getStatus()) {
                        order.setCity(address.getCity());
                        order.setPhoneNumber(address.getPhoneNumber());
                        order.setDistrict(address.getDistrict());
                        order.setWard(address.getWard());
                        order.setRecipientName(address.getFullName());
                        order.setStreetDetails(address.getStreetDetails());
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

    @Transactional
    public Map<String, Object> orderCheckoutUpdate(OrderUpdateRequestDTO orderReq, HttpSession session) {

        // Xóa thuộc tính 'totalPayment' khỏi session nếu có
        session.removeAttribute("totalPayment");

        // Lấy các tham số từ yêu cầu
        Long promotionId = orderReq.getPromotionId();
        Long addressId = orderReq.getAddressId();
        String shippingMethod = orderReq.getShippingMethod();
        String paymentMethod = orderReq.getPaymentMethod();

        Map<String, Object> response = new HashMap<>();

        // Khởi tạo Optional cho promotion
        Optional<Coupon> promotionById = Optional.empty();

        // Tìm đơn hàng cần cập nhật
        Order order = this.findOrderByStatusAndCreatedBy();

        if (order != null) {
            // Cập nhật địa chỉ nếu có
            if (addressId != null) {
                Address addressById = this.addressService.findAddressById(addressId);
                if (addressById != null) {
                    order.setAddress(addressById);
                    AddressDTO dto = new AddressDTO();
                    dto.setFullName(addressById.getFullName());
                    dto.setPhoneNumber(addressById.getPhoneNumber());
                    dto.setStreetDetails(addressById.getStreetDetails());
                    dto.setStatus(addressById.getStatus());
                    session.setAttribute("isChangeAddress", "true");
                    response.put("addressById", dto);
                }
            }

            // Cập nhật phương thức thanh toán nếu có
            if (paymentMethod != null) {
                order.setPaymentMethod(paymentMethod);
            }

            // Cập nhật mã giảm giá nếu có
            if (promotionId != null) {
                promotionById = this.promotionService.findCouponById(promotionId);
                if (promotionById.isPresent()) {
                    Coupon coupon = promotionById.get();
                    // Kiểm tra ngày hết hạn và trạng thái của coupon
                    if (coupon.getEndDate() != null &&
                            (coupon.getEndDate().isAfter(LocalDateTime.now())
                                    || coupon.getEndDate().isEqual(LocalDateTime.now()))
                            &&
                            Boolean.TRUE.equals(coupon.getStatus())) {
                        order.setCoupon(coupon);
                        session.setAttribute("promotionInOrder", coupon);
                    } else {
                        // Nếu coupon hết hạn hoặc không còn hiệu lực
                        promotionById = Optional.empty();
                        order.setCoupon(null);
                        session.removeAttribute("promotionInOrder");
                    }
                }
            }

            // Cập nhật phương thức vận chuyển nếu có
            if (shippingMethod != null) {
                if (shippingMethod.equalsIgnoreCase("EXPRESS")) {
                    order.setShippingMethod(ShippingMethod.EXPRESS);
                } else if (shippingMethod.equalsIgnoreCase("FAST")) {
                    order.setShippingMethod(ShippingMethod.FAST);
                } else {
                    order.setShippingMethod(ShippingMethod.SAVE);
                }
            }

            // Lưu các thay đổi vào cơ sở dữ liệu
            this.orderRepository.save(order);

            // Nếu có thay đổi về phương thức vận chuyển hoặc mã giảm giá, tính toán lại
            // tổng thanh toán
            if (shippingMethod != null || promotionId != null) {
                String emailUser = (String) session.getAttribute("email");

                // Lấy ra tổng tiền hàng từ giỏ hàng
                List<CartDetail> lstCartDetail = this.cartService.getAllCartDetailByCart(emailUser);
                BigDecimal totalPrice = BigDecimal.ZERO;
                for (CartDetail cartDetail : lstCartDetail) {
                    // Giả sử cartDetail.getPrice() trả về double, chuyển đổi sang BigDecimal
                    totalPrice = totalPrice.add(BigDecimal.valueOf(cartDetail.getPrice()));
                }

                // Tính phí vận chuyển
                BigDecimal shippingPrice;
                ShippingMethod shippingMethodEnum = order.getShippingMethod();
                switch (shippingMethodEnum) {
                    case EXPRESS:
                        shippingPrice = BigDecimal.valueOf(50000);
                        break;
                    case FAST:
                        shippingPrice = BigDecimal.valueOf(30000);
                        break;
                    default:
                        shippingPrice = BigDecimal.valueOf(20000);
                        break;
                }

                // Tính tổng thanh toán trước giảm giá
                BigDecimal totalPayment = totalPrice.add(shippingPrice);

                // Tính giảm giá
                BigDecimal discountValue = BigDecimal.ZERO;
                if (promotionById.isPresent()) {
                    Coupon coupon = promotionById.get();
                    // Kiểm tra nếu tổng thanh toán đạt tối thiểu để áp dụng mã giảm giá
                    if (totalPayment.compareTo(BigDecimal.valueOf(coupon.getMinimumValue())) >= 0) {
                        if (coupon.getDiscountType() == DiscountType.FIXED) {
                            if (coupon.getDiscountValueFixed() != null) {
                                discountValue = BigDecimal.valueOf(coupon.getDiscountValueFixed());
                            }
                        } else if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
                            if (coupon.getDiscountValuePercent() != null) {
                                BigDecimal percent = BigDecimal.valueOf(coupon.getDiscountValuePercent())
                                        .divide(BigDecimal.valueOf(100));
                                BigDecimal calculatedDiscount = totalPayment.multiply(percent);
                                if (coupon.getMaximumReduction() != null) {
                                    discountValue = calculatedDiscount
                                            .min(BigDecimal.valueOf(coupon.getMaximumReduction()));
                                } else {
                                    discountValue = calculatedDiscount;
                                }
                            }
                        }
                    } else {
                        // Nếu không đạt tối thiểu, không áp dụng giảm giá
                        discountValue = BigDecimal.ZERO;
                        // Có thể thêm thông báo lỗi hoặc cảnh báo ở đây nếu cần
                        response.put("message", "false");
                    }
                } else if (promotionId == null && order.getCoupon() != null) {
                    Coupon coupon = order.getCoupon();
                    if (totalPayment.compareTo(BigDecimal.valueOf(coupon.getMinimumValue())) >= 0) {
                        if (coupon.getDiscountType() == DiscountType.FIXED) {
                            if (coupon.getDiscountValueFixed() != null) {
                                discountValue = BigDecimal.valueOf(coupon.getDiscountValueFixed());
                            }
                        } else if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
                            if (coupon.getDiscountValuePercent() != null) {
                                BigDecimal percent = BigDecimal.valueOf(coupon.getDiscountValuePercent())
                                        .divide(BigDecimal.valueOf(100));
                                BigDecimal calculatedDiscount = totalPayment.multiply(percent);
                                if (coupon.getMaximumReduction() != null) {
                                    discountValue = calculatedDiscount
                                            .min(BigDecimal.valueOf(coupon.getMaximumReduction()));
                                } else {
                                    discountValue = calculatedDiscount;
                                }
                            }
                        }
                    } else {
                        // Nếu không đạt tối thiểu, không áp dụng giảm giá
                        discountValue = BigDecimal.ZERO;
                        // Có thể thêm thông báo lỗi hoặc cảnh báo ở đây nếu cần
                        response.put("message", "Đơn hàng không đủ giá trị tối thiểu để áp dụng mã giảm giá.");
                    }
                }

                // Trừ giảm giá từ tổng thanh toán
                totalPayment = totalPayment.subtract(discountValue);

                // Cập nhật tổng thanh toán vào đơn hàng
                order.setTotalAmount(totalPayment);
                this.orderRepository.save(order);

                // Cập nhật các thuộc tính vào session
                session.setAttribute("shippingMethodInOrder", order.getShippingMethod());
                session.setAttribute("totalPayment", totalPayment);

                // Đưa thông tin vào response
                response.put("totalPayment", totalPayment);
                response.put("shippingPrice", shippingPrice);
                if (discountValue.compareTo(BigDecimal.ZERO) > 0) {
                    response.put("discountValue", discountValue);
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
            if (order.getOrderStatus() != OrderStatus.PENDING_INVOICE) {
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

        }

        return orderSRes;
    }

    public void updateOrderDetail(DataUpdateOrderDetailDTO dto, String idOrderD) {
        Optional<OrderDetail> findOrderDetailById = this.findOrderDetailById(Long.valueOf(idOrderD));
        if (findOrderDetailById.isPresent()) {
            OrderDetail orderDUnwrap = findOrderDetailById.get();
            if (dto.isUpdateORemove()) {
                Optional<ProductDetail> productDetailById = this.productDetailService
                        .findProductDetailById(dto.getProductDetailId());

                if (productDetailById.isPresent()) {
                    Double newPrice = productDetailById.get().getPrice() * dto.getQuantity();
                    orderDUnwrap.setPrice(newPrice);

                    long amountDifference = 0;
                    if (orderDUnwrap.getQuantity() > dto.getQuantity()) {
                        amountDifference = orderDUnwrap.getQuantity() - dto.getQuantity();
                        int newQuantity = productDetailById.get().getQuantity() + (int) amountDifference;
                        productDetailById.get().setQuantity(newQuantity);
                    } else {
                        amountDifference = dto.getQuantity() - orderDUnwrap.getQuantity();
                        int newQuantityProductDetail = productDetailById.get().getQuantity() - (int) amountDifference;
                        productDetailById.get().setQuantity(newQuantityProductDetail);
                    }

                }
                orderDUnwrap.setQuantity(dto.getQuantity());
                this.orderDetailRepository.save(orderDUnwrap);
                this.productDetailService.saveProductDetail(productDetailById.get());

            } else {
                Optional<ProductDetail> productDetailById = this.productDetailService
                        .findProductDetailById(dto.getProductDetailId());
                if (dto.isRestocking()) {
                    long newPrice = productDetailById.get().getQuantity() + orderDUnwrap.getQuantity();
                    int intPrice = (int) newPrice;
                    productDetailById.get().setQuantity(intPrice);
                    this.productDetailService.saveProductDetail(productDetailById.get());
                }

                this.orderDetailRepository.delete(orderDUnwrap);
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
        if (order == null) {
            return null;
        }
        if (orderDTO.getPaymentStatus() == PaymentStatus.COMPLETED) {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
        }
        if (orderDTO.getPromotion() != null) {
            if (orderDTO.getPromotion().getId() != 0) {
                order.setCoupon(Coupon.builder().id(orderDTO.getPromotion().getId()).build());
            }
        }
        order.setNote(orderDTO.getNote());
        order.setTotalAmount(orderDTO.getTotalAmount());
        order.setTotalProducts(orderDTO.getTotalProducts());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        if (orderDTO.getUser() != null) {
            if (orderDTO.getUser().getId() == 0) {
                order.setUser(null);
            } else {
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
    public void deleteInvoiceDetail(Long id) {
        orderDetailRepository.deleteByOrderDetailIdAndProductDetailId(id);
    }

    public List<OrderDTO> getOrderNonPendingAndPos(DeliveryStatus deliveryStatus, PaymentStatus paymentStatus) {
        Pageable pageable = PageRequest.of(0, 5);
        List<Order> listOrder = orderRepository.getAllOrderNonPendingAndPos(deliveryStatus, paymentStatus, pageable);
        List<OrderDTO> listOrderDTO = listOrder.stream().map(OrderDTO::toOrderDTO).collect(Collectors.toList());
        return listOrderDTO;
    }

    public List<OrderDetailDTO> getOrderDetailByOrderId(Long id) {
        List<OrderDetail> orderDetails = orderDetailRepository.getOrderDetailByOrderId(id);

        orderDetails
                .forEach(item -> System.out.println(item.getProductDetail().getPrice() + ": giá sản phẩm trong này"));
        List<OrderDetailDTO> orderDetailDTOS = orderDetails.stream().map(OrderDetailDTO::toOrderDetailDTO)
                .collect(Collectors.toList());
        return orderDetailDTOS;
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.getAllOrderById(id).orElse(null);
        OrderDTO orderDTO = OrderDTO.toOrderDTO(order);
        return orderDTO;
    }
}
