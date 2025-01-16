package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Material;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findByMaterialNameContainingIgnoreCase(String materialName, Pageable pageable);

    boolean existsByMaterialName(String materialName);

    boolean existsByMaterialNameAndIdNot(String materialName, Long id);
    @Query(value = "SELECT [id]\n" +
            "      ,[material_name]\n" +
            "      ,[status]\n" +
            "      ,[created_date]\n" +
            "      ,[updated_date]\n" +
            "  FROM [dbo].[materials]\n" +
            "WHERE [status] = 0",nativeQuery = true)
    List<Material> getAllActive();

    @Query("SELECT c FROM Material c WHERE c.materialName = :materialName")
    List<Material> finByName(@Param("materialName") String materialName);
}
