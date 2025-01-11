package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Coupon;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Coupon, Long>{
    List<Coupon> findByMinOrderValueLessThanEqual(Double orderValue);

//    List<ProductResponse> getListProductNoneDiscount();

}
