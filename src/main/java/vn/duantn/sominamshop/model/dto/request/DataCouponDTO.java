package vn.duantn.sominamshop.model.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataCouponDTO {
    private String couponCode;

    private boolean discountType;// kiểu giảm

    private Double discountValueFixed; // số tiền giảm

    private Integer discountValuePercent;// sô phần trăm giảm

    private Double maximumReduction;// giá trị giảm tối đa cho kiểu giảm phần trăm

    private Double minimumValue;// giá trị đơn hàng tối thiểu được sử dụng mã giảm gía

    private Integer usageLimit;// giới hạn sử dụng

    private String startDate;

    private String endDate;
}
