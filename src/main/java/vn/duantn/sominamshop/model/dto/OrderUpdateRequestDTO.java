package vn.duantn.sominamshop.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateRequestDTO {
    private Long addressId;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String shippingMethod;
    private Long promotionId;
}
