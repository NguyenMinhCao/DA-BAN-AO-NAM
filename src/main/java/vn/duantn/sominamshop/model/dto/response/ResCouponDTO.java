package vn.duantn.sominamshop.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.constants.DiscountType;

@Getter
@Setter
public class ResCouponDTO {
    private Long id;
    private String couponCode;
    private DiscountType discountType;
    private Double discountValueFixed;
    private Integer discountValuePercent;
    private Double maximumReduction;
    private Double minimumValue;
    private Integer usageLimit;
    private boolean status;
    private String startDate;
    private String endDate;
    private String createdAt;
}
