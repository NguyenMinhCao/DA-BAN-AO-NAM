package vn.duantn.sominamshop.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCheckoutDTO {
    private String address;
    private BigDecimal totalAmount;
}
