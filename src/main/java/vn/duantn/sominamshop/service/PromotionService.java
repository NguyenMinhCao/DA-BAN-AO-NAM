package vn.duantn.sominamshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Promotion;
import vn.duantn.sominamshop.repository.PromotionRepository;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<Promotion> findAllPromotion() {
        return this.promotionRepository.findAll();
    }

    public Optional<Promotion> findPromotionById(Long id) {
        return this.promotionRepository.findById(id);
    }
}
