package vn.duantn.sominamshop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadService {

    private final ServletContext servletContext;

    public UploadService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        if (file == null) {
            return null;
        }
        String finalName = "";
        try {
            byte[] bytes = file.getBytes();
            String rootPath = this.servletContext.getRealPath("/resources/images");
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists())
                dir.mkdirs();
            // Create the file on server
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalName;
    }

    public String handleSaveAvatar(MultipartFile file, String uploadDir){
        System.out.println(file + "ảnh ");
        if (file != null && !file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String rootPath = this.servletContext.getRealPath(uploadDir);
                String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                String uniqueFileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "." + extension;
                Path filePath = Paths.get(rootPath, uniqueFileName);
//                Files.createDirectories(filePath.getParent());
                file.transferTo(filePath.toFile());
                return uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
