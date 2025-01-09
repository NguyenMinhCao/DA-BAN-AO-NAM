package vn.duantn.sominamshop.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.duantn.sominamshop.model.Image;
import vn.duantn.sominamshop.model.Product;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
//    List<Image> findByProduct(Product product);
    @Query(value = "SELECT [id]\n" +
            "      ,[productDetail]\n" +
            "      ,[name]\n" +
            "      ,[url_image]\n" +
            "      ,[status]\n" +
            "      ,[created_date]\n" +
            "      ,[updated_date]\n" +
            "  FROM [dbo].[images]\n" +
            "WHERE [productDetail] = :id",nativeQuery = true)
    List<Image> getALlByProductId(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM [dbo].[images]\n" +
            "      WHERE [product_details] = :productId",nativeQuery = true)
    void deleteAllByProductId(@Param("productId") Integer productId);
}



