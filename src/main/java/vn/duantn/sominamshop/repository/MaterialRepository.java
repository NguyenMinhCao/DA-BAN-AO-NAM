package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Material;
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findByMaterialNameContainingIgnoreCase(String materialName, Pageable pageable);

}
