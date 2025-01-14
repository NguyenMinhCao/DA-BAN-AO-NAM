package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.Order;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {
//    List<Coupon> findByMinOrderValueLessThanEqual(Double orderValue);

    // List<ProductResponse> getListProductNoneDiscount();

}
