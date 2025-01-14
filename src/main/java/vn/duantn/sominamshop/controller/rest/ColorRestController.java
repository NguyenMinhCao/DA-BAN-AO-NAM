package vn.duantn.sominamshop.controller.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.dto.request.ColorRequest;
import vn.duantn.sominamshop.service.ColorService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/rest/color")
public class ColorRestController {
    @Autowired
    ColorService colorService;

    @PostMapping("/checkDuplicateName")
    public ResponseEntity<?> checkDuplicateName(@RequestBody ColorRequest colorRequest){
        List<Color> lists = colorService.findByName(colorRequest.getName());
        boolean isDuplicateName = false;
        if(lists.isEmpty()){
            isDuplicateName = true;
        }
        return ResponseEntity.ok(Map.of("isDuplicateName",isDuplicateName));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Color color) {
        return ResponseEntity.ok(colorService.add(color));
    }

    @GetMapping("/formUpdate/{id}")
    public ResponseEntity<?> formUpdate(@PathVariable("id") Long id, Model model) {
        Color color = colorService.findById(id);
        if (color != null) {
            model.addAttribute("color", color);
            return ResponseEntity.ok(color);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody Color color, @PathVariable("id") Long id) {
        return ResponseEntity.ok(colorService.update(color, id));
    }

    @PostMapping("/setStatus/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return ResponseEntity.ok(colorService.setStatus(id));
    }
}
