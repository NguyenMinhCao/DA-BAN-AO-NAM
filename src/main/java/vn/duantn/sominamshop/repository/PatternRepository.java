package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Pattern;

import java.util.List;

@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {
    @Query(value = "SELECT [id]\n" +
            "      ,[pattern_name]\n" +
            "      ,[status]\n" +
            "      ,[created_date]\n" +
            "      ,[updated_date]\n" +
            "  FROM [dbo].[patterns]\n" +
            "WHERE [status] = 0",nativeQuery = true)
    List<Pattern> getAllActive();
}
