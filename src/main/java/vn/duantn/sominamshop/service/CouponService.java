package vn.duantn.sominamshop.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.constants.DiscountType;
import vn.duantn.sominamshop.model.dto.request.DataCouponDTO;
import vn.duantn.sominamshop.model.dto.CouponDTO;
import vn.duantn.sominamshop.model.dto.response.ResCouponDTO;
import vn.duantn.sominamshop.model.dto.response.ResultPaginationDTO;
import vn.duantn.sominamshop.repository.CouponRepository;

@Service
public class CouponService {
    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<Coupon> findAllCoupon() {
        return this.couponRepository.findAll();
    }

    public Optional<Coupon> findCouponById(Long id) {
        return this.couponRepository.findById(id);
    }

    public ResultPaginationDTO fetchAllCoupons(Specification<Coupon> spec, Pageable pageable) {
        Page<Coupon> page = this.couponRepository.findAll(spec, pageable);

        List<Coupon> lstCoupons = page.getContent();

        List<ResCouponDTO> couponRes = convertCouponToCouponResponse(lstCoupons);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(page.getSize());
        mt.setPages(page.getTotalPages());
        mt.setTotal(page.getTotalElements());
        mt.setCurrentPageElements(page.getNumberOfElements());

        rs.setMeta(mt);
        rs.setResult(couponRes);
        return rs;
    }

    public List<ResCouponDTO> convertCouponToCouponResponse(List<Coupon> lstCoupon) {
        List<ResCouponDTO> couponSRes = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Coupon coupon : lstCoupon) {
            ResCouponDTO newCouponRes = new ResCouponDTO();
            newCouponRes.setId(coupon.getId());
            newCouponRes.setCouponCode(coupon.getCouponCode());
            newCouponRes.setDiscountType(coupon.getDiscountType());
            newCouponRes.setDiscountValuePercent(coupon.getDiscountValuePercent());
            newCouponRes.setDiscountValueFixed(coupon.getDiscountValueFixed());
            newCouponRes.setStatus(coupon.getStatus());
            newCouponRes.setUsageLimit(coupon.getUsageLimit());
            newCouponRes.setMaximumReduction(coupon.getMaximumReduction());
            newCouponRes.setMinimumValue(coupon.getMaximumReduction());

            String formattedDateCreate = coupon.getCreatedAt().format(formatter);
            newCouponRes.setCreatedAt(formattedDateCreate);

            String formattedStartDate = coupon.getStartDate().format(formatter);
            newCouponRes.setStartDate(formattedStartDate);

            String formattedEndDate = coupon.getEndDate().format(formatter);
            newCouponRes.setEndDate(formattedEndDate);

            couponSRes.add(newCouponRes);
        }

        return couponSRes;
    }

    public Coupon findCouponByCode(String code) {
        return this.couponRepository.findByCouponCode(code);
    }

    public void saveCoupon(DataCouponDTO dto) {
        Coupon coupon = new Coupon();
        coupon.setCouponCode(dto.getCouponCode());
        coupon.setDiscountType(dto.isDiscountType() == true ? DiscountType.FIXED : DiscountType.PERCENTAGE);
        coupon.setDiscountValueFixed(dto.getDiscountValueFixed());
        coupon.setDiscountValuePercent(dto.getDiscountValuePercent());
        coupon.setMaximumReduction(dto.getMaximumReduction());
        coupon.setMinimumValue(dto.getMinimumValue());
        coupon.setStatus(true);
        coupon.setUsageLimit(dto.getUsageLimit());

        // Định dạng ngày
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Chuyển đổi LocalDate thành LocalDateTime tại đầu ngày (00:00)
        LocalDateTime startDateTime = LocalDateTime.parse(dto.getStartDate(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(dto.getEndDate(), formatter);

        coupon.setStartDate(startDateTime);
        coupon.setEndDate(endDateTime);
        this.couponRepository.save(coupon);
    }

    public Coupon updateCoupon(DataCouponDTO dto, Coupon coupon) {
        coupon.setCouponCode(dto.getCouponCode());
        coupon.setDiscountType(dto.isDiscountType() == true ? DiscountType.FIXED : DiscountType.PERCENTAGE);
        coupon.setDiscountValueFixed(dto.getDiscountValueFixed());
        coupon.setDiscountValuePercent(dto.getDiscountValuePercent());
        coupon.setMaximumReduction(dto.getMaximumReduction());
        coupon.setMinimumValue(dto.getMinimumValue());
        coupon.setStatus(true);
        coupon.setUsageLimit(dto.getUsageLimit());

        // Định dạng ngày
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Chuyển đổi LocalDate thành LocalDateTime tại đầu ngày (00:00)
        LocalDateTime startDateTime = LocalDateTime.parse(dto.getStartDate(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(dto.getEndDate(), formatter);

        coupon.setStartDate(startDateTime);
        coupon.setEndDate(endDateTime);
        return this.couponRepository.save(coupon);
    }

    public List<Coupon> findAllCouponsOrderByDiscountsDesc() {
        return this.couponRepository.findAllCouponsOrderByDiscountsDesc();
    }

    // public List<PromotionDTO> getPromotion(Double orderValue) {
    // LocalDate today = LocalDate.now();
    // List<PromotionDTO> listPromotionDTO =
    // couponRepository.findByMinOrderValueLessThanEqual(orderValue).stream()
    // .map(PromotionDTO::toPromotionDTO)
    // .filter(promotionDTO -> promotionDTO.getStartDateAsLocalDate() != null
    // && promotionDTO.getStartDateAsLocalDate().isBefore(today)
    // || promotionDTO.getStartDateAsLocalDate().equals(today))
    // .filter(promotionDTO -> promotionDTO.getEndDateAsLocalDate() != null
    // && promotionDTO.getEndDateAsLocalDate().isAfter(today))
    // .filter(promotionDTO -> promotionDTO.getUsageLimit() != null &&
    // promotionDTO.getUsageLimit() > 0)
    // .filter(promotionDTO -> promotionDTO.isStatus())
    // .collect(Collectors.toList());
    // return listPromotionDTO;
    // }

    public List<CouponDTO> findValidCoupons(String code) {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.of("+07:00"));
        List<Coupon> couponList = couponRepository.findValidCoupons(localDateTime, code);
        List<CouponDTO> couponDTOList = couponList.stream().map(CouponDTO::toDTO).collect(Collectors.toList());
        return couponDTOList;
    }

    @Transactional
    public void updateUsageLimitCoupon(Integer quantity, Long id) {
        couponRepository.updateQuantity(quantity, id);
    }
}