package vn.duantn.sominamshop.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;
import vn.duantn.sominamshop.model.constants.DiscountType;
import vn.duantn.sominamshop.util.SecurityUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "promotions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "promotion_code")
    private String promotionCode;

    @Column(name = "discount_type")
//    @Enumerated(EnumType.STRING)
    private String discountType;

    @Column(name = "discount_value")
    private String discountValue;

    @Column(name = "min_order_value", precision = 10, scale = 2)
    private BigDecimal minOrderValue;

    @Column(name = "usage_limit ")
    private Integer usageLimit;

    @Column(name = "start_date ")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "status")
    private boolean status;


    @ManyToMany(mappedBy = "promotions")
    private List<Product> products;

    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void handleBeforeCreate() {
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
