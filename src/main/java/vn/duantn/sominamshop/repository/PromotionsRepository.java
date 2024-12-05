package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Promotion;

import java.util.Optional;

@Repository
public interface PromotionsRepository extends JpaRepository<Promotion, Long> {



}
