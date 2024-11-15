package vn.duantn.sominamshop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.duantn.sominamshop.model.Image;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.service.ImageService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.UploadService;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;
    private final ImageService imageService;

    public ProductController(ProductService productService, UploadService uploadService, ImageService imageService) {
        this.productService = productService;
        this.uploadService = uploadService;
        this.imageService = imageService;
    }

    @GetMapping("/admin/product")
    public String getMethodName(Model model) {
        List<Product> listProducts = productService.getAllProduct();
        model.addAttribute("listProducts", listProducts);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getProductCreate(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create-product")
    public String postMethodName(@ModelAttribute("newProduct") @Valid Product product,
            BindingResult newBindingResult,
            @RequestParam("getImgFiles") MultipartFile[] files) {

        List<Image> lstImage = new ArrayList<>();
        if (newBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        Product productNew = this.productService.handleSaveProduct(product);

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String avatar = this.uploadService.handleSaveUploadFile(file, "product");
                Image img = new Image();
                img.setImageUrl(avatar);
                img.setProduct(product);
                lstImage.add(img);
                this.imageService.handleSaveImage(img);

            }
        }

        Product productById = this.productService.findProductById(productNew.getId());
        productById.setImages(lstImage);
        this.productService.handleSaveProduct(productById);
        return "redirect:/admin/product";
    }

}
