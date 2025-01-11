package vn.duantn.sominamshop.model.dto.response;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.constants.DeliveryStatus;
import vn.duantn.sominamshop.model.constants.OrderStatus;
import vn.duantn.sominamshop.model.constants.PaymentStatus;

@Getter
@Setter
public class ResOrderDTO {
    private long id;
    private String createAt;
    private String fullName;
    private String orderSource;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private PaymentStatus paymentStatus;
    private DeliveryStatus deliveryStatus;
}
