package vn.duantn.sominamshop.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.model.constants.DiscountType;

@Getter
@Setter
public class ResCouponDTO {
    private long id;
    private String couponCode;
    private DiscountType discountType;
    private double discountValueFixed;
    private int discountValuePercent;
    private double maximumReduction;
    private double minimumValue;
    private int usageLimit;
    private boolean status;
    private String startDate;
    private String endDate;
    private String createdAt;
}
