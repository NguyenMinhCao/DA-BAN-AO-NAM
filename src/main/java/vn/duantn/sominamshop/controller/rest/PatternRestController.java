package vn.duantn.sominamshop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duantn.sominamshop.service.OriginService;
import vn.duantn.sominamshop.service.PatternService;

@RestController
@RequestMapping("/admin/rest/pattern")
public class PatternRestController {

    @Autowired
    PatternService patternService;


    @PostMapping("/setStatus/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return ResponseEntity.ok(patternService.setStatus(id));
    }
}
