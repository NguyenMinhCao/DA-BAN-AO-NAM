package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByMinOrderValueLessThanEqual(Double orderValue);

//    List<ProductResponse> getListProductNoneDiscount();

    @Query("SELECT c FROM Coupon c WHERE c.startDate <= :currentDate AND c.endDate >= :currentDate AND c.status = true")
    List<Coupon> findValidCoupons(@Param("currentDate") LocalDateTime currentDate);

}
