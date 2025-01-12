package vn.duantn.sominamshop.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.constants.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDTO {
    private long id;

    private String couponCode;

    private DiscountType discountType;

    private String discountValue;

    private BigDecimal minOrderValue;

    private Integer usageLimit;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean status;

    public static CouponDTO toDTO(Coupon coupon){
        if(coupon == null){
            return null;
        }
        return CouponDTO.builder()
                .id(coupon.getId())
                .couponCode(coupon.getCouponCode())
                .endDate(coupon.getEndDate())
                .startDate(coupon.getStartDate())
                .discountType(coupon.getDiscountType())
                .minOrderValue(coupon.getMinOrderValue())
                .usageLimit(coupon.getUsageLimit())
                .status(coupon.isStatus())
                .discountValue(coupon.getDiscountValue())
                .build();
    }
}
