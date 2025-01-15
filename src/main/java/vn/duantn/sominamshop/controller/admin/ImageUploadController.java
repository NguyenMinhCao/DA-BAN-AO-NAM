package vn.duantn.sominamshop.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/upload")
public class ImageUploadController {
    // Đường dẫn đến thư mục lưu ảnh trong thư mục resources/images
    private static final String UPLOAD_DIR = "src/main/webapp/resources/images/product";

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            Path uploadDirPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadDirPath)) {
                Files.createDirectories(uploadDirPath); // Tạo thư mục
            }

            // Tạo tên tệp tin cho ảnh
            String fileName = "image_" + System.currentTimeMillis() + ".jpg";
            Path filePath = uploadDirPath.resolve(fileName);

            // Lưu ảnh vào thư mục
            Files.write(filePath, file.getBytes());

            // Trả về URL ảnh
            String imageUrl = "/uploads/images/product/" + fileName;

            // Thêm thông tin vào response
            response.put("imageUrl", imageUrl);
            response.put("imageName", fileName);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace(); // In lỗi ra log
            response.put("error", "Failed to upload image");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
