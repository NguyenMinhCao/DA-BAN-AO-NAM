package vn.duantn.sominamshop.service;

import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Image;
import vn.duantn.sominamshop.repository.ImageRepository;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void handleSaveImage(Image img) {
        this.imageRepository.save(img);
    }
}
