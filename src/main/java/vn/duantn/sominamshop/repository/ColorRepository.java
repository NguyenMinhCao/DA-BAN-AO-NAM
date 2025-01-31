package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Color;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Page<Color> findByColorNameContainingIgnoreCase(String colorName, Pageable pageable);

    boolean existsByColorName(String colorName);

    boolean existsByColorNameAndIdNot(String colorName, Long id);




    @Query(value = "SELECT [id]\n" +
            "      ,[color_name]\n" +
            "      ,[status]\n" +
            "      ,[created_date]\n" +
            "      ,[updated_date]\n" +
            "  FROM [dbo].[colors]\n" +
            "WHERE [status] = 0",nativeQuery = true)
    List<Color> getAllActive();

    @Query("SELECT c FROM Color c WHERE c.colorName = :colorName")
    List<Color> findByName(@Param("colorName") String colorName);

    @Query(value = """
                SELECT DISTINCT c.id, c.color_name, c.status, c.created_date, c.updated_date
                FROM colors c
                INNER JOIN
                    product_details ON c.id = product_details.color_id
                WHERE
                    product_details.product_id =:productId
                    AND c.status = 0
            """, nativeQuery = true)
    List<Color> findDistinctByProductDetails_ProductId(@Param("productId") Integer productId);
}
