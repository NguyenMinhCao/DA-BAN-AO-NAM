package vn.duantn.sominamshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;
import vn.duantn.sominamshop.model.constants.ShippingMethod;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private long id;
    private Integer totalProducts;
    private CouponDTO promotion;
    private String note;

    private BigDecimal totalAmount;

    private ShippingMethod shippingMethod;

    private String paymentMethod;

    private DeliveryStatus deliveryStatus;

    private PaymentStatus paymentStatus;

    private Boolean orderSource;

    private UserDTO user;

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
