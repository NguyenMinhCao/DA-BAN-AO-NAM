package vn.duantn.sominamshop.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Image;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.Size;
import vn.duantn.sominamshop.model.dto.request.ProductRequest;
import vn.duantn.sominamshop.model.dto.rest.ProductRestRequest;
import vn.duantn.sominamshop.repository.ColorRepository;
import vn.duantn.sominamshop.repository.ImageRepository;
import vn.duantn.sominamshop.repository.SizeRepository;
import vn.duantn.sominamshop.service.ProductService;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/admin/rest/add-product")
public class AddProductRestController {

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductService productService;

    @PostMapping("/checkDuplicateName")
    public ResponseEntity<?> checkDuplicateName(@RequestBody ProductRequest productRequest){
        List<Product> lists = productService.findByName(productRequest.getName());
        boolean isDuplicateName = false;
        if(lists.isEmpty()){
            isDuplicateName = true;
        }
        return ResponseEntity.ok(Map.of("isDuplicateName",isDuplicateName));
    }

    @GetMapping()
    public ResponseEntity<?> getAll(){
        List<Color> listColor = colorRepository.getAllActive();
        List<Size> listSize = sizeRepository.getAllActive();


        ProductRestRequest productRestRequest = new ProductRestRequest(listColor,listSize);

        return ResponseEntity.ok(productRestRequest);
    }
}
