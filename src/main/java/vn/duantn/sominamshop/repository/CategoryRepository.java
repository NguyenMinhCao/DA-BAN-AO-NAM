package vn.duantn.sominamshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.model.Color;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByCategoryNameContainingIgnoreCase(String categoryName, Pageable pageable);

    boolean existsByCategoryName(String categoryName);

    boolean existsByCategoryNameAndIdNot(String categoryName, Long id);
    @Query(value = "SELECT [id]\n" +
            "      ,[category_name]\n" +
            "      ,[status]\n" +
            "      ,[created_date]\n" +
            "      ,[updated_date]\n" +
            "  FROM [dbo].[categories]\n" +
            "WHERE [status] = 0",nativeQuery = true)
    List<Category> getAllActive();

    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    List<Category> findByName(@Param("categoryName") String categoryName);
}
