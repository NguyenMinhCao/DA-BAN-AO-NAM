package vn.duantn.sominamshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Promotion;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    private long id;
    private DeliveryStatus status;
    private User user;
    private Promotion promotion;
    private String note;
    private BigDecimal totalAmount;
    private String paymentMethod;

    public static OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(order.getId())
                .paymentMethod("COD")
                .note(order.getNote())
                .status(order.getDeliveryStatus())
                .user(order.getUser())
                .totalAmount(order.getTotalAmount())
                .build();
        return orderDTO;
    }
}
