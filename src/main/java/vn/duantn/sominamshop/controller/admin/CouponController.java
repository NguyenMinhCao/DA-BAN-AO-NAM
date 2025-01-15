package vn.duantn.sominamshop.controller.admin;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turkraft.springfilter.boot.Filter;

import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.dto.request.DataCouponDTO;
import vn.duantn.sominamshop.model.dto.response.ResultPaginationDTO;
import vn.duantn.sominamshop.service.CouponService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/coupon")
    public String getViewCoupon(Model model, @Filter Specification<Coupon> spec, Pageable pageable) {
        ResultPaginationDTO lstCoupons = this.couponService.fetchAllCoupons(spec, pageable);

        model.addAttribute("lstCoupons", lstCoupons.getResult());
        return "admin/coupon/show";
    }

    @GetMapping("/coupon/search")
    public ResponseEntity<?> fetchCoupon(@Filter Specification<Coupon> spec, Pageable pageable) {
        System.out.println("sdf");
        return ResponseEntity.ok().body(this.couponService.fetchAllCoupons(spec, pageable));
    }

    @GetMapping("/coupon/{id}/edit")
    public String getDetailCoupon(@PathVariable Long id, Model model) {
        Optional<Coupon> couponById = this.couponService.findCouponById(id);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String startDate = couponById.get().getStartDate().format(formatter);
        String endDate = couponById.get().getEndDate().format(formatter);

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("couponById", couponById.get());
        return "admin/coupon/detail";
    }

    @PutMapping("/coupon/{id}/edit")
    public ResponseEntity<Coupon> getDetailCouponEdit(@PathVariable Long id, Model model, @RequestBody DataCouponDTO dto) {
        Optional<Coupon> couponById = this.couponService.findCouponById(id);
      
        return ResponseEntity.ok().body(this.couponService.updateCoupon(dto, couponById.get()));
    }

    @GetMapping("/coupon/add")
    public String getAddCoupon(Model model) {
        return "admin/coupon/add";
    }

    @PostMapping("/coupon/add")
    public ResponseEntity<Boolean> getAddPostCoupon(@RequestBody DataCouponDTO dto) {
        this.couponService.saveCoupon(dto);
        return ResponseEntity.ok().body(true);
    }

}
