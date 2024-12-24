package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Origin;
@Repository
public interface OriginRepository extends JpaRepository<Origin, Long> {
}
