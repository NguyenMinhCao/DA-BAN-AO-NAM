package vn.duantn.sominamshop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.dto.request.ImageRequest;
import vn.duantn.sominamshop.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/admin/rest/image")
public class ImageRestController {
    @Autowired
    ImageService imageService;

    @GetMapping("/findByProductId/{productId}")
    public ResponseEntity<?> findByProductId(@PathVariable("productId") Integer id){
        return ResponseEntity.ok(imageService.getALlByProducctId(id));
    }
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ImageRequest imageRequest) {
        try {
            // Thêm ảnh vào ProductDetail đã có sẵn
            imageService.save(imageRequest);
            return ResponseEntity.ok("Thêm ảnh thành công");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi: " + e.getMessage());
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ImageRequest imageRequest) {
        return ResponseEntity.ok(imageService.add(imageRequest));
    }

    @DeleteMapping("/deleteAll/{productId}")
    public ResponseEntity<?> deleteAll(@PathVariable("productId") Long productId){
        imageService.deleteAllByProductId(productId);
        return ResponseEntity.ok("Xóa thành công ảnh với ProductId: " + productId);
    }

}
