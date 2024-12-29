package vn.duantn.sominamshop.service;

import org.springframework.stereotype.Service;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService( CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }

}
