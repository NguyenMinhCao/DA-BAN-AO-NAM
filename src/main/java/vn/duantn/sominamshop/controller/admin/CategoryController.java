package vn.duantn.sominamshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.service.CategoryService;
import vn.duantn.sominamshop.service.ColorService;
@Controller
@RequestMapping("/admin/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategory(
            @RequestParam(value = "categoryName", required = false, defaultValue = "") String categoryName,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model) {

        Page<Category> categoryPage = categoryService.getCategory(categoryName, page);
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("categoryName", categoryName);
        return "admin/category/show";
    }

    @GetMapping("/create")
    public String showCreateCategory(Model model) {
        model.addAttribute("newCategory", new Category());
        return "admin/category/create";
    }

    @PostMapping("/create")
    public String createColor(@ModelAttribute("newCategory") Category newCategory, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "admin/category/create";
        }
        categoryService.addCategory(newCategory);
        return "redirect:/admin/category";
    }
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "admin/category/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute("category") Category  category, RedirectAttributes redirectAttributes) {
        categoryService.updateCategory(id, category);
        redirectAttributes.addFlashAttribute("success", "Danh mục đã được cập nhật thành công!");
        return "redirect:/admin/category";
    }


}
