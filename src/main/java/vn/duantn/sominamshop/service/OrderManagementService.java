package vn.duantn.sominamshop.service;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;
import vn.duantn.sominamshop.model.dto.request.DataStatusOrderDTO;

@Service
public class OrderManagementService {
    private final OrderService orderService;
    private final OrderHistoryService orderHistoryService;

    public OrderManagementService(OrderService orderService, OrderHistoryService orderHistoryService) {
        this.orderService = orderService;
        this.orderHistoryService = orderHistoryService;
    }

    public void updateStatusOrder(Order order, DataStatusOrderDTO dataStatus) {
        if (dataStatus.getFieldUpdate().equals("DELIVERY")) {
            if (dataStatus.getStatus().equals("COMPLETED")) {
                order.setDeliveryStatus(DeliveryStatus.COMPLETED);
                this.orderService.saveOrder(order);
                this.orderHistoryService.updateDeliveryStatus(order , "COMPLETED");
            } else if (dataStatus.getStatus().equals("DELIVERY")) {
                order.setDeliveryStatus(DeliveryStatus.DELIVERY);
                this.orderService.saveOrder(order);
                this.orderHistoryService.updateDeliveryStatus(order , "DELIVERY");
            } else {
                order.setDeliveryStatus(DeliveryStatus.PENDING);
                this.orderService.saveOrder(order);
                this.orderHistoryService.updateDeliveryStatus(order , "PENDING");
            }
        } else {
            order.setPaymentStatus(PaymentStatus.COMPLETED);
            this.orderService.saveOrder(order);
            // this.orderHistoryService.updateDeliveryStatus(orderById.get());
        }
    }

}
