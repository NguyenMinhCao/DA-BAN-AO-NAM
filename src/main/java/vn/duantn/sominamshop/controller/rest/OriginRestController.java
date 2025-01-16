package vn.duantn.sominamshop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.duantn.sominamshop.service.ColorService;
import vn.duantn.sominamshop.service.OriginService;

@RestController
@RequestMapping("/admin/rest/origin")
public class OriginRestController {
    @Autowired
    OriginService originService;


    @PostMapping("/setStatus/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(originService.setStatus(id));
    }

}
