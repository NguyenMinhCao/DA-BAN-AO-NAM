package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Origin;

import java.util.List;

@Repository
public interface OriginRepository extends JpaRepository<Origin, Long> {
    Page<Origin> findByOriginNameContainingIgnoreCase(String originName, Pageable pageable);
    @Query(value = "SELECT [origin_id]\n" +
            "      ,[origin_name]\n" +
            "      ,[status]\n" +
            "      ,[created_at]\n" +
            "      ,[updated_at]\n" +
            "  FROM [dbo].[origins]\n" +
            "WHERE [status] = 0",nativeQuery = true)
    List<Origin> getAllActive();
}
