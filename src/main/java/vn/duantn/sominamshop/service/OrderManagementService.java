package vn.duantn.sominamshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;
import vn.duantn.sominamshop.model.dto.request.CheckQuantityProductDeDTO;
import vn.duantn.sominamshop.model.dto.request.DataStatusOrderDTO;
import vn.duantn.sominamshop.repository.OrderDetailRepository;

@Service
public class OrderManagementService {
    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;
    private final ProductDetailService productDetailService;
    private final OrderDetailRepository orderDetailRepository;

    public OrderManagementService(OrderService orderService, OrderHistoryService orderHistoryService,
            ProductDetailService productDetailService, OrderDetailRepository orderDetailRepository) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
        this.productDetailService = productDetailService;
        this.orderDetailRepository = orderDetailRepository;
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

    public void updateStatusOrder(Order order, DataStatusOrderDTO dataStatus) {
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
            // this.orderHistoryService.updateDeliveryStatus(orderById.get());
        }
    }

    // xủ lý trả hàng
    public Boolean returnProduct(CheckQuantityProductDeDTO dto) {
        int saveTypeText = 0;
        int productNumber = 0;
        Optional<OrderDetail> orderDetailById = this.orderDetailRepository.findById(dto.getOrderDetailId());
        if (orderDetailById.isPresent()) {
            OrderDetail orderDetail = orderDetailById.get();

            // cộng sản phẩm vào productDetail
            int newQuantity = (int) (orderDetail.getProductDetail().getQuantity() + dto.getQuantityValue());
            orderDetail.getProductDetail().setQuantity(newQuantity);
            this.productDetailService.saveProductDetail(orderDetail.getProductDetail());

            if (orderDetail.getQuantity() == dto.getQuantityValue()) {
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
            Optional<Order> orderById = this.orderService.findOrderById(dto.getOrderDetailId());

            if (orderById.isPresent()) {
                this.orderHistoryService.updateReturnProduct(saveTypeText, productNumber, orderById.get());
                return true;
            }
            return false;
        } else {
            return false;
        }

    }

}
