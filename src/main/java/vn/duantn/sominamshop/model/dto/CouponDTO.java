package vn.duantn.sominamshop.model.dto;


import jakarta.persistence.*;
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
    private DiscountType discountType;// kiểu giảm
    private double discountValueFixed; //số tiền giảm

    private int discountValuePercent;//sô phần trăm giảm

    private double maximumReduction;// giá trị giảm tối đa cho kiểu giảm phần trăm

    private double minimumValue;// giá trị đơn hàng tối thiểu được sử dụng mã giảm gía

    private int usageLimit;// giới hạn sử dụng

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
                .status(coupon.isStatus())
                .usageLimit(coupon.getUsageLimit())
                .discountType(coupon.getDiscountType())
                .endDate(coupon.getEndDate())
                .startDate(coupon.getStartDate())
                .discountValueFixed(coupon.getDiscountValueFixed())
                .discountValuePercent(coupon.getDiscountValuePercent())
                .minimumValue(coupon.getMinimumValue())
                .maximumReduction(coupon.getMaximumReduction())
                .build();
    }
}
