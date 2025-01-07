package vn.duantn.sominamshop.controller.rest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.Material;
import vn.duantn.sominamshop.model.dto.request.MaterialRequest;
import vn.duantn.sominamshop.service.MaterialService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/rest/material")
public class MaterialRestController {
    @Autowired
    MaterialService materialService;

    @PostMapping("/checkDuplicateName")
    public ResponseEntity<?> checkDuplicateName(@RequestBody MaterialRequest materialRequest) {
        List<Material> lists = materialService.findByName(materialRequest.getName());
        boolean isDuplicateName = false;
        if (lists.isEmpty()) {
            isDuplicateName = true;
        }
        return ResponseEntity.ok(Map.of("isDuplicateName", isDuplicateName));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Material material) {
        return ResponseEntity.ok(materialService.add(material));
    }

    @GetMapping("/formUpdate/{id}")
    public ResponseEntity<?> formUpdate(@PathVariable("id") Long id, Model model) {
        Material material = materialService.findById(id);
        if (material != null) {
            model.addAttribute("material", material);
            return ResponseEntity.ok(material);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Material material, @PathVariable("id") Long id) {
        return ResponseEntity.ok(materialService.update(material, id));
    }

    @PostMapping("/setStatus/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return ResponseEntity.ok(materialService.setStatus(id));
    }
}
