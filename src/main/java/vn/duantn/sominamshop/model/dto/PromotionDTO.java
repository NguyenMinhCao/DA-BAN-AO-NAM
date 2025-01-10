package vn.duantn.sominamshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.constants.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromotionDTO {
    private long id;
    private String promotionCode;
    private String discountType;
    private String discountValue;
    private BigDecimal minOrderValue;
    private Integer usageLimit;
    private String startDate;
    private String endDate;
    private boolean status;

    public LocalDate getStartDateAsLocalDate() {
        return LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getEndDateAsLocalDate() {
        return LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static PromotionDTO toPromotionDTO(Coupon promotion) {
        PromotionDTO promotionDTO = PromotionDTO.builder()
                .id(promotion.getId())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .status(promotion.isStatus())
                // .discountType(promotion.getDiscountType())
                .discountValue(promotion.getDiscountValue())
                // .promotionCode(promotion.getPromotionCode())
                .usageLimit(promotion.getUsageLimit())
                .minOrderValue(promotion.getMinOrderValue())
                .build();
        return promotionDTO;
    }
}
