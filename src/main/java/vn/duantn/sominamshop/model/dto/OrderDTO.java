package vn.duantn.sominamshop.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.User;

import vn.duantn.sominamshop.model.*;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;
import vn.duantn.sominamshop.model.constants.ShippingMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private long id;
    private DeliveryStatus status;

    private Integer totalProducts;

    private BigDecimal totalAmount;

    private String note;

    private ShippingMethod shippingMethod;

    private String paymentMethod;

    private DeliveryStatus deliveryStatus;

    private PaymentStatus paymentStatus;

    private Boolean orderSource;

    private UserDTO user;

    private Coupon promotion;

    public static OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(order.getId())
                .paymentMethod(order.getPaymentMethod())
                .note(order.getNote())
                .deliveryStatus(order.getDeliveryStatus())
                .user(UserDTO.toDTO(order.getUser()))
                .totalAmount(order.getTotalAmount())
                .totalProducts(order.getTotalProducts())
                .paymentStatus(order.getPaymentStatus())
                .build();
        return orderDTO;
    }
}
