package vn.duantn.sominamshop.model.dto;

import java.math.BigDecimal;

public interface CounterProductProjection {
    Long getId();
    String getName();
    Long getQuantity();
    String getSizeName();
    String getColorName();
    BigDecimal getPrice();
    String getImage();
}
