package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Material;
@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
}
