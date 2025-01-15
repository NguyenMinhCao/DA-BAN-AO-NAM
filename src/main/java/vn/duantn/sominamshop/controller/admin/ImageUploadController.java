package vn.duantn.sominamshop.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.duantn.sominamshop.service.UploadService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class ImageUploadController {

    private final UploadService uploadService;

    public ImageUploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    // Đường dẫn đến thư mục lưu ảnh trong thư mục resources/images
    private static final String UPLOAD_DIR = "src/main/webapp/resources/images/product";

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        String imgProduct = this.uploadService.handleSaveAvatar(file, "product");
        if (imgProduct != null) {
            String imageUrl = imgProduct;
            response.put("imageUrl", imageUrl);
        } else {
            response.put("error", "Không thể tải ảnh lên.");
        }

        return ResponseEntity.ok(response);
    }

}
