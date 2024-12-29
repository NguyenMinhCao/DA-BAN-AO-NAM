package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Color;
@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
}
