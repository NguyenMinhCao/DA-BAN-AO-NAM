package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Size;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Page<Size> findBySizeNameContainingIgnoreCase(String sizeName, Pageable pageable);
    boolean existsBySizeName(String sizeName);

    boolean existsBySizeNameAndIdNot(String sizeName, Long id);

    @Query(value = """
                SELECT DISTINCT s.id, s.size_name, s.status, s.created_date, s.updated_date
                FROM sizes s
                INNER JOIN
                    product_details pd ON s.id = pd.size_id
                WHERE
                    pd.product_id =:productId
                    AND s.status = 0
            """, nativeQuery = true)
    List<Size> findDistinctByIdAndName(@Param("productId") Integer productId);

    @Query(value = """
                SELECT s.id, s.size_name, s.status, s.created_date, s.updated_date
                FROM sizes s
                INNER
                    JOIN product_details pd ON s.id = pd.size_id
                WHERE
                    pd.product_id = :productId
                    AND pd.color_id = :colorId
                    AND pd.quantity > 0;
            """, nativeQuery = true)
    List<Size> findSizeByProductIdAndColorId(@Param("productId") Integer productId,
                                             @Param("colorId") Integer colorId);
    @Query(value = "SELECT [id]\n" +
            "      ,[size_name]\n" +
            "      ,[status]\n" +
            "      ,[created_date]\n" +
            "      ,[updated_date]\n" +
            "  FROM [dbo].[sizes]\n" +
            "WHERE [status] = 0",nativeQuery = true)
    List<Size> getAllActive();

    @Query("SELECT c FROM Size c WHERE c.sizeName = :sizeName")
    List<Size> findByName(@Param("sizeName") String sizeName);
}
