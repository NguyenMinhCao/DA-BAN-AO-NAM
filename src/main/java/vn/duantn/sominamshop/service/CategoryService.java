package vn.duantn.sominamshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.repository.CategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryService {
//
//    private final CategoryRepository categoryRepository;
//
//    public CategoryService( CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//
//    }
//    public List<Category> getAllCategories() {
//        return this.categoryRepository.findAll();
//    }
//
//
//    public Page<Category> getCategory(String categoryName, int page) {
//        Pageable pageable = PageRequest.of(page, 5);
//        if (categoryName.isEmpty()) {
//            return categoryRepository.findAll(pageable);
//        } else {
//            return categoryRepository.findByCategoryNameContainingIgnoreCase(categoryName, pageable);
//        }
//    }
//
//    public Category getCategoryById(Long id) {
//        return categoryRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy : " + id));
//    }
//
//    public Category addCategory(Category category) {
//        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
//            throw new IllegalArgumentException("Tên  danh mục không được để trống");
//        }
//        return categoryRepository.save(category);
//    }
//
//    public Category updateCategory(Long id, Category updatedCategory) {
//        Category existing = getCategoryById(id);
//        if (updatedCategory.getCategoryName() != null && !updatedCategory.getCategoryName().isEmpty()) {
//            existing.setCategoryName(updatedCategory.getCategoryName());
//        }
//        if (updatedCategory.getStatus() != null) {
//            existing.setStatus(updatedCategory.getStatus());
//        }
//        return categoryRepository.save(existing);
//    }






    private final CategoryRepository categoryRepository;


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public List<Category> getAllActive() {
        return categoryRepository.getAllActive();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public List<Category> findByName(String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category, Long id) {
        Category searchCategory = categoryRepository.findById(id).get();
        if (searchCategory != null) {
            searchCategory.setCategoryName(category.getCategoryName());
            return categoryRepository.save(searchCategory);
        }
        return null;
    }

    public Category setStatus(Long id) {
        Category searchCategory = categoryRepository.findById(id).get();
        if (searchCategory != null) {
            if (searchCategory.getStatus() == 1) {
                searchCategory.setStatus(0);
            } else {
                searchCategory.setStatus(1);
            }
            return categoryRepository.save(searchCategory);
        }
        return null;
    }
}
