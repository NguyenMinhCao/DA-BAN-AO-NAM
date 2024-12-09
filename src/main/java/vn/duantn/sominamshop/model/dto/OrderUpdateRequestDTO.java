package vn.duantn.sominamshop.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateRequestDTO {
    private String address;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String shippingMethod;
    private Long promotionId;
}
