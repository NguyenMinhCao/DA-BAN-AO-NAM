package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long>{
    
}
