package vn.duantn.sominamshop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.dto.request.ImageRequest;
import vn.duantn.sominamshop.service.ImageService;

@RestController
@RequestMapping("/admin/rest/image")
public class ImageRestController {
    @Autowired
    ImageService imageService;

    @GetMapping("/findByProductId/{productId}")
    public ResponseEntity<?> findByProductId(@PathVariable("productId") Integer id){
        return ResponseEntity.ok(imageService.getALlByProductId(id));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ImageRequest imageRequest) {
        return ResponseEntity.ok(imageService.add(imageRequest));
    }

    @DeleteMapping("/deleteAll/{productId}")
    public ResponseEntity<?> deleteAll(@PathVariable("productId") Integer productId){
        imageService.deleteAllByProductId(productId);
        return ResponseEntity.ok("Xóa thành công ảnh với ProductId: " + productId);
    }
}
