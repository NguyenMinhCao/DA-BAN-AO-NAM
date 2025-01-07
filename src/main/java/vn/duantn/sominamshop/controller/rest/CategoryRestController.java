package vn.duantn.sominamshop.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.Category;
import vn.duantn.sominamshop.model.dto.request.CategoryRequest;
import vn.duantn.sominamshop.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/rest/category")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/checkDuplicateName")
    public ResponseEntity<?> checkDuplicateName(@RequestBody CategoryRequest categoryRequest){
        List<Category> lists = categoryService.findByName(categoryRequest.getName());
        boolean isDuplicateName = false;
        if(lists.isEmpty()){
            isDuplicateName = true;
        }
        return ResponseEntity.ok(Map.of("isDuplicateName",isDuplicateName));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.add(category));
    }

    @GetMapping("/formUpdate/{id}")
    public ResponseEntity<?> formUpdate(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.findById(id);
        if (category != null) {
            model.addAttribute("category", category);
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Category category, @PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.update(category, id));
    }

    @PostMapping("/setStatus/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryService.setStatus(id));
    }
}
