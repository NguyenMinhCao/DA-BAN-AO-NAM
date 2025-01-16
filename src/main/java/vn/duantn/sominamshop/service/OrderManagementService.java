package vn.duantn.sominamshop.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.OrderHistory;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.OrderStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;
import vn.duantn.sominamshop.model.dto.request.AddressUpdateRequest;
import vn.duantn.sominamshop.model.dto.request.CheckQuantityProductDeDTO;
import vn.duantn.sominamshop.model.dto.request.DataStatusOrderDTO;
import vn.duantn.sominamshop.model.dto.response.ResOrderDTO;
import vn.duantn.sominamshop.repository.OrderDetailRepository;
import vn.duantn.sominamshop.util.SecurityUtil;

@Service
public class OrderManagementService {
    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;
    private final ProductDetailService productDetailService;
    private final OrderDetailRepository orderDetailRepository;
    private final UserService userService;

    public OrderManagementService(OrderService orderService, OrderHistoryService orderHistoryService,
            ProductDetailService productDetailService, OrderDetailRepository orderDetailRepository,
            UserService userService) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
        this.productDetailService = productDetailService;
        this.orderDetailRepository = orderDetailRepository;
        this.userService = userService;
    }

    public boolean checkQuantityProduct(CheckQuantityProductDeDTO dto) {
        ProductDetail productDetailByID = this.productDetailService.findProductDetailById(dto.getProductDetailID())
                .get();
        if (productDetailByID != null) {
            if (productDetailByID.getQuantity() >= dto.getQuantityValue()) {
                return true;
            }
        }
        return false;
    }

    public Boolean updateStatusOrder(Order order, DataStatusOrderDTO dataStatus) {
        List<OrderDetail> lstOrderDetails = order.getOrderDetails();
        for (OrderDetail orderDetail : lstOrderDetails) {
            ProductDetail productDetail = orderDetail.getProductDetail();
            long quantityOrderDetail = orderDetail.getQuantity();
            if (quantityOrderDetail <= productDetail.getQuantity()) {
                Integer newQuantityProduct = productDetail.getQuantity() - (int) quantityOrderDetail;
                productDetail.setQuantity(newQuantityProduct);
                this.productDetailService.saveProductDetail(productDetail);
            } else {
                return false;
            }
        }

        if (dataStatus.getFieldUpdate().equals("DELIVERY")) {
            if (dataStatus.getStatus().equals("COMPLETED")) {
                order.setDeliveryStatus(DeliveryStatus.COMPLETED);
                this.orderService.saveOrder(order);
                this.orderHistoryService.updateDeliveryStatus(order, "COMPLETED");
            } else if (dataStatus.getStatus().equals("DELIVERY")) {
                order.setDeliveryStatus(DeliveryStatus.DELIVERY);
                this.orderService.saveOrder(order);
                this.orderHistoryService.updateDeliveryStatus(order, "DELIVERY");
            } else {
                order.setDeliveryStatus(DeliveryStatus.PENDING);
                this.orderService.saveOrder(order);
                this.orderHistoryService.updateDeliveryStatus(order, "PENDING");
            }
        } else {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
            this.orderService.saveOrder(order);
            this.orderHistoryService.updateDeliveryStatus(order, dataStatus.getStatus());
        }
        return true;
    }   

    // xủ lý trả hàng
    public Boolean returnProduct(CheckQuantityProductDeDTO dto) {
        int saveTypeText = 0;
        int productNumber = 0;
        boolean isRestocking = false;
        Optional<Order> orderById = this.orderService.findOrderById(dto.getOrderId());
        Optional<OrderDetail> orderDetailById = this.orderDetailRepository.findById(dto.getOrderDetailId());
        if (orderDetailById.isPresent()) {
            OrderDetail orderDetail = orderDetailById.get();

            // cộng sản phẩm vào productDetail
            if (dto.isRestocking()) {
                int newQuantity = (int) (orderDetail.getProductDetail().getQuantity() + dto.getQuantityValue());
                orderDetail.getProductDetail().setQuantity(newQuantity);
                this.productDetailService.saveProductDetail(orderDetail.getProductDetail());
                isRestocking = true;
            }

            if (orderById.get().getOrderDetails().size() == 1 && orderDetail.getQuantity() == dto.getQuantityValue()) {
                this.orderService.deleteOrderDetail(dto.getOrderDetailId(), dto.getProductDetailID());
                orderById.get().setOrderStatus(OrderStatus.CANCELED);
                saveTypeText = 3;
            } else if (orderDetail.getQuantity() == dto.getQuantityValue()) {
                productNumber = (int) (dto.getQuantityValue());
                this.orderService.deleteOrderDetail(dto.getOrderDetailId(), dto.getProductDetailID());
                saveTypeText = 1;
            } else {
                long newQuantityInOrderDetail = orderDetail.getQuantity() - dto.getQuantityValue();
                productNumber = (int) (dto.getQuantityValue());
                orderDetail.setQuantity(newQuantityInOrderDetail);
                saveTypeText = 0;
            }
            this.orderDetailRepository.save(orderDetail);

            if (orderById.isPresent()) {
                this.orderHistoryService.updateReturnProduct(saveTypeText, productNumber, orderById.get(), isRestocking,
                        dto.getDescription());
                return true;
            }
            return false;
        } else {
            return false;
        }

    }

    public List<ResOrderDTO> findAllOrderSearch(String query) {
        String orderId = query;
        boolean isOrderId = true;

        // try {
        if (query.startsWith("#")) {
            String idStr = query.substring(1);
            orderId = idStr;
            isOrderId = true;
        } else if (!query.startsWith("0")) {
            orderId = query;
            isOrderId = true;
        } else {
            isOrderId = false;
        }

        if (isOrderId) {
            List<Order> getAllListOrderById = this.orderService.getOrdersByIdPrefix(orderId);
            return convertOrderToOrderSearchResponse(getAllListOrderById);
        }

        List<Order> listOrders = new ArrayList<>();
        if (query.startsWith("0")) {
            List<User> lstUsreByPhone = this.userService.findUserByPhone(query);
            for (User user : lstUsreByPhone) {
                List<Order> findOrdersByUser = this.orderService.findOrderByUser(user);
                for (Order orderUser : findOrdersByUser) {
                    listOrders.add(orderUser);
                }
            }
            return convertOrderToOrderSearchResponse(listOrders);
        }
        return null;
    }

    public List<ResOrderDTO> convertOrderToOrderSearchResponse(List<Order> lstOrder) {
        List<ResOrderDTO> orderSRes = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Order order : lstOrder) {
            ResOrderDTO newOrderRes = new ResOrderDTO();
            newOrderRes.setDeliveryStatus(order.getDeliveryStatus());
            newOrderRes.setId(order.getId());
            newOrderRes.setFullName(
                    order.getUser().getFullName() != null ? order.getUser().getFullName() : "Không tồn tại");
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

    public Boolean updateTextNote(String textNote, Order order) {
        OrderHistory orderHis = new OrderHistory();
        orderHis.setOrder(order);

        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        if (email != null) {
            User userByEmail = this.userService.findUserByEmail(email);
            if (userByEmail.getRole().getName().equals("USER")) {
                orderHis.setPerformedBy("Hệ thống");
            } else {
                orderHis.setPerformedBy(userByEmail.getFullName());
            }
        }

        orderHis.setDescription("Đã cập nhật ghi chú");

        order.setNote(textNote);
        this.orderService.saveOrder(order);

        this.orderHistoryService.saveOrderHistory(orderHis);

        return true;
    }

    public Order updateAddressOrder(AddressUpdateRequest addressUpdateRequest, Order order) {
        order.setRecipientName(addressUpdateRequest.getFullName());
        order.setPhoneNumber(addressUpdateRequest.getPhoneNumber());
        order.setWard(addressUpdateRequest.getWard());
        order.setDistrict(addressUpdateRequest.getDistrict());
        order.setCity(addressUpdateRequest.getCity());
        order.setStreetDetails(addressUpdateRequest.getStreetDetails());
        this.orderService.saveOrder(order);
        this.orderHistoryService.updateAddressOrder(order);
        return order;
    }
}
