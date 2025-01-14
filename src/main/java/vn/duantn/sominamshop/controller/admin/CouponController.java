package vn.duantn.sominamshop.controller.admin;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turkraft.springfilter.boot.Filter;

import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.service.CouponService;

@Controller
@RequestMapping("/admin")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/coupon")
    public ResponseEntity<?> fetchCoupon(@Filter Specification<Coupon> spec, Pageable pageable) {

        return ResponseEntity.ok().body(this.couponService.fetchAllCoupons(spec, pageable));
    }

    @GetMapping("/coupon/edit")
    public String getDetailCoupon() {
        return "admin/coupon/edit";
    }

    @GetMapping("/coupon/add")
    public String getAddCoupon() {
        return "admin/coupon/add";
    }
}
