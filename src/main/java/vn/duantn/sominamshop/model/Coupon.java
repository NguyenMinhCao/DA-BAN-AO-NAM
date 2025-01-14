package vn.duantn.sominamshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.*;

import vn.duantn.sominamshop.model.constants.DiscountType;
import vn.duantn.sominamshop.util.SecurityUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "discount_type")
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;// kiểu giảm

    @Column(name = "discount_value_fixed")
    private Double discountValueFixed; // số tiền giảm

    @Column(name = "discount_value_percent")
    private Integer discountValuePercent;// sô phần trăm giảm

    @Column(name = "maximum_reduction")
    private Double maximumReduction;// giá trị giảm tối đa cho kiểu giảm phần trăm

    @Column(name = "minimum_value")
    private Double minimumValue;// giá trị đơn hàng tối thiểu được sử dụng mã giảm gía

    @Column(name = "usage_limit ")
    private Integer usageLimit;// giới hạn sử dụng

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private boolean status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = LocalDateTime.now();

        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
    }
}
