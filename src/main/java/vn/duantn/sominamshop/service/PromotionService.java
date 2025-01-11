package vn.duantn.sominamshop.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.dto.PromotionDTO;
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

    public List<PromotionDTO> getPromotion(Double orderValue){
        LocalDate today = LocalDate.now();
        List<PromotionDTO> listPromotionDTO = promotionRepository.findByMinOrderValueLessThanEqual(orderValue).stream()
                .map(PromotionDTO::toPromotionDTO)
                .filter(promotionDTO -> promotionDTO.getStartDateAsLocalDate() != null && promotionDTO.getStartDateAsLocalDate().isBefore(today) || promotionDTO.getStartDateAsLocalDate().equals(today))
                .filter(promotionDTO -> promotionDTO.getEndDateAsLocalDate() !=null && promotionDTO.getEndDateAsLocalDate().isAfter(today))
                .filter(promotionDTO -> promotionDTO.getUsageLimit() != null && promotionDTO.getUsageLimit() > 0)
                .filter(promotionDTO -> promotionDTO.isStatus())
                .collect(Collectors.toList());
        return listPromotionDTO;
    }
}