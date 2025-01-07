package vn.duantn.sominamshop.model.dto;

import java.math.BigDecimal;

public interface CounterProductProjection {
    Long getId();
    String getName();
    Integer getQuantity();
    String getSizeName();
    String getColorName();
    BigDecimal getPrice();
    String getImage();
}
