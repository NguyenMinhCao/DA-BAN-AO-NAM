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
@Query(value = "SELECT img.[id], " +
        "       img.[product_detail_id], " +
        "       pd.[product_id], " +
        "       img.[name], " +
        "       img.[url_image], " +
        "       img.[status], " +
        "       img.[created_date], " +
        "       img.[updated_date] " +
        "FROM [dbo].[images] img " +
        "JOIN [dbo].[product_details] pd ON img.[product_detail_id] = pd.[id] " +
        "WHERE pd.[product_id] = :id",
        nativeQuery = true)
List<Image> getAllByProductId(@Param("id") Integer id);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM [dbo].[images]\n" +
            "      WHERE [product_details] = :productId",nativeQuery = true)
    void deleteAllByProductId(@Param("productId") Integer productId);
}



