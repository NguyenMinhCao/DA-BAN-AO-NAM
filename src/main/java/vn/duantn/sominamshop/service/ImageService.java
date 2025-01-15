package vn.duantn.sominamshop.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import vn.duantn.sominamshop.model.Image;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.dto.request.ImageRequest;
import vn.duantn.sominamshop.repository.ImageRepository;
import vn.duantn.sominamshop.repository.ProductDetailRepository;

@Service
@RequiredArgsConstructor

public class ImageService {
//    private final ImageRepository imageRepository;
//
//    public ImageService(ImageRepository imageRepository) {
//        this.imageRepository = imageRepository;
//    }
//
//    public void handleSaveImage(Image img) {
//        this.imageRepository.save(img);
//    }
//
//
//
//    public List<Image> updateProductImages(Product product, List<Image> newImages, MultipartFile[] files) throws IOException {
//        List<Image> existingImages = product.getImages();
//        List<Long> newImageIds = newImages != null ?
//                newImages.stream().map(Image::getId).toList() : new ArrayList<>();
//
//        // Xóa ảnh cũ không còn trong danh sách
//        existingImages.stream()
//                .filter(img -> !newImageIds.contains(img.getId()))
//                .forEach(img -> {
//                    try {
//                        Path path = Paths.get("src", "main", "webapp", "resources", "images", "product", img.getImageUrl());
//                        Files.deleteIfExists(path);
//                        deleteImageById(img.getId());
//                    } catch (IOException e) {
//                        throw new RuntimeException("Không thể xóa ảnh: " + img.getImageUrl(), e);
//                    }
//                });
//
//        // Thêm ảnh mới
//        List<Image> updatedImages = new ArrayList<>(newImages != null ? newImages : new ArrayList<>());
//        if (files != null) {
//            for (int i = 0; i < files.length; i++) {
//                if (!files[i].isEmpty()) {
//                    String fileExtension = Objects.requireNonNull(files[i].getOriginalFilename())
//                            .substring(files[i].getOriginalFilename().lastIndexOf('.'));
//                    String fileName = UUID.randomUUID().toString() + fileExtension;
//                    Path imagePath = Paths.get("src", "main", "webapp", "resources", "images", "product", fileName);
//                    Files.createDirectories(imagePath.getParent());
//                    Files.copy(files[i].getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
//
//                    Image img = new Image();
//                    img.setImageUrl(fileName);
//                    img.setProduct(product);
//                    img.setIsMain(i == 0); // Ảnh đầu tiên là ảnh chính
//                    updatedImages.add(img);
//                    handleSaveImage(img);
//                }
//            }
//        }
//
//        return updatedImages;
//    }
//
//    public void deleteImageById(Long id) {
//        if (imageRepository.existsById(id)) {
//            imageRepository.deleteById(id);
//        } else {
//            throw new IllegalArgumentException("Hình ảnh không tồn tại với id: " + id);
//        }
//    }
//    public void deleteImagesByProduct(Product product) {
//        // Tìm tất cả các ảnh liên kết với sản phẩm
//        List<Image> images = imageRepository.findByProduct(product);
//
//        for (Image image : images) {
//            // Xoá file ảnh trên hệ thống
//            Path imagePath = Paths.get("src", "main", "webapp", "resources", "images", "product", image.getImageUrl());
//            try {
//                if (Files.exists(imagePath)) {
//                    Files.delete(imagePath); // Xoá file nếu tồn tại
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException("Không thể xóa file ảnh: " + image.getImageUrl());
//            }
//
//            // Xoá bản ghi ảnh khỏi database
//            imageRepository.delete(image);
//        }
//    }


    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;


    public List<Image> getALlByProducctId(Integer id) {
        return imageRepository.getAllByProductId(id);
    }


    public void save(ImageRequest imageRequest) {
        // Lấy danh sách ProductDetail từ productId
        List<ProductDetail> productDetails = productDetailRepository.findByProductId(imageRequest.getProductId());

        // Nếu không tìm thấy ProductDetail nào
        if (productDetails.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy ProductDetail cho productId: " + imageRequest.getProductId());
        }

        // Lặp qua từng ProductDetail
        for (ProductDetail productDetail : productDetails) {
            // Kiểm tra xem ảnh đã có trong ProductDetail này chưa, tránh việc thêm ảnh trùng
            if (imageRepository.existsByProductDetailAndUrlImage(productDetail, imageRequest.getUrlImage())) {
                throw new IllegalArgumentException("Ảnh đã tồn tại trong ProductDetail này.");
            }

            // Tiến hành lưu ảnh vào ProductDetail
            Image image = new Image();
            image.setProductDetail(productDetail);
            image.setUrlImage(imageRequest.getUrlImage());
            image.setStatus(imageRequest.getStatus());
            image.setIsMain(imageRequest.getIsMain());

            // Lưu ảnh vào cơ sở dữ liệu
            imageRepository.save(image);
        }
    }



    public List<ProductDetail> getProductDetailsByProductId(Long productId) {
        return productDetailRepository.findByProductId(productId);
    }
    public String deleteAllByProductId(Long productId) {
        imageRepository.deleteAllByProductId(productId);
        return "Xóa thành công ảnh với ProductId: " + productId;
    }

    public Image add(ImageRequest imageRequest) {
        Image image = imageRequest.map(new Image());
        return imageRepository.save(image);
    }
}
