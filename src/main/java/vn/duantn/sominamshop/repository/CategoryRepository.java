package vn.duantn.sominamshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.duantn.sominamshop.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
