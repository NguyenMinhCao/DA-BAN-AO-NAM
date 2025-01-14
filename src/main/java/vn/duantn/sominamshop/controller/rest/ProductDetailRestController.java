package vn.duantn.sominamshop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.dto.request.ProductDetailRequest;
import vn.duantn.sominamshop.service.ProductDetailService;
@RestController
@RequestMapping("/admin/rest/product-detail")
public class ProductDetailRestController {

    @Autowired
    ProductDetailService productDetailService;

    @GetMapping("/getListSizeAddProductDetail")
    public ResponseEntity<?> getListSizeAddProductDetail(@RequestParam Long productId, @RequestParam Long colorId){
        return ResponseEntity.ok(productDetailService.getListSizeAddProductDetail(productId, colorId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ProductDetailRequest productDetailRequest) {
        return ResponseEntity.ok(productDetailService.add(productDetailRequest));
    }


    @GetMapping("/formUpdate/{id}")
    public ResponseEntity<?> formUpdate(@PathVariable("id") Long id, Model model) {
        ProductDetail productDetail = productDetailService.findById(id);
        if (productDetail != null) {
            model.addAttribute("productDetail", productDetail);
            return ResponseEntity.ok(productDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody ProductDetail productDetail, @PathVariable("id") Long id) {
        return ResponseEntity.ok(productDetailService.update(productDetail, id));
    }

    @PostMapping("/setStatus/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productDetailService.setStatus(id));
    }
}
