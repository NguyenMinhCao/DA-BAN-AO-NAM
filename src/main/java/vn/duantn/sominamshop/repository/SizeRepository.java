package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Size;
@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Page<Size> findBySizeNameContainingIgnoreCase(String sizeName, Pageable pageable);

}
