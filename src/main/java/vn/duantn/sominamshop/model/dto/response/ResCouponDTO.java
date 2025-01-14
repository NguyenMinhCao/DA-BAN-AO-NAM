package vn.duantn.sominamshop.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.constants.DiscountType;

@Getter
@Setter
public class ResCouponDTO {
    private long id;
    private String couponCode;
    private DiscountType discountType;
    private String discountValue;
    private BigDecimal minOrderValue;
    private Integer usageLimit;
    private String startDate;
    private String endDate;
    private boolean status;
    private String createdAt;
}
