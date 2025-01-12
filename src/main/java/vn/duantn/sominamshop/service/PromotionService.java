package vn.duantn.sominamshop.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.dto.CouponDTO;
import vn.duantn.sominamshop.repository.PromotionRepository;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<Coupon> findAllPromotion() {
        return this.promotionRepository.findAll();
    }

    public Optional<Coupon> findPromotionById(Long id) {
        return this.promotionRepository.findById(id);
    }

    public List<CouponDTO> findValidCoupons(){
        LocalDateTime localDateTime =  LocalDateTime.now(ZoneOffset.of("+07:00"));
        List<Coupon> couponList = promotionRepository.findValidCoupons(localDateTime);
        List<CouponDTO> couponDTOList = couponList.stream().map(CouponDTO :: toDTO).collect(Collectors.toList());
        return couponDTOList;
    }

//    public
}