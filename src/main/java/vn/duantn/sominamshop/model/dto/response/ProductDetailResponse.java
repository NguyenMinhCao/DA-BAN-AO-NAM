package vn.duantn.sominamshop.model.dto.response;

import java.math.BigDecimal;

public interface ProductDetailResponse {
    Integer getId();
    String getColorName();
    String getSizeName();
    Integer getQuantity();
    BigDecimal getPrice();
    Integer getStatus();
}
