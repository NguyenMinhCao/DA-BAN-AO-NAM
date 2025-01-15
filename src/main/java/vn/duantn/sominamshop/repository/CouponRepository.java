package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {

//    List<Coupon> findByMinOrderValueLessThanEqual(Double orderValue);

    Coupon findByCouponCode(String code);
    // List<Coupon> findByMinOrderValueLessThanEqual(Double orderValue);

//    List<Coupon> findByMinOrderValueLessThanEqual(Double orderValue);



    // List<ProductResponse> getListProductNoneDiscount();

    @Query("SELECT c FROM Coupon c " +
            "WHERE c.startDate <= :currentDate " +
            "AND c.endDate >= :currentDate " +
            "AND c.status = true " +
            "AND c.couponCode like concat('%', :code, '%') ")
    List<Coupon> findValidCoupons(@Param("currentDate") LocalDateTime currentDate, @Param("code") String code);

    @Modifying
    @Query("Update Coupon c set c.usageLimit = c.usageLimit - :quantity where c.id = :id")
    Integer updateQuantity(@Param("quantity") Integer quantity, @Param("id") Long id);
}
