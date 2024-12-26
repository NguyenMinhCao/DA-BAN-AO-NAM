package vn.duantn.sominamshop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.duantn.sominamshop.model.*;
import vn.duantn.sominamshop.service.*;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;
    private final ImageService imageService;
    private final SizeService sizeService;
    private final CategoryService categoryService;
    private final ColorService colorService;
    private final MaterialService materialService;
    private final OriginService originService;
    private final PatternService patternService;


    public ProductController(ProductService productService, UploadService uploadService, ImageService imageService, SizeService sizeService, CategoryService categoryService, ColorService colorService, MaterialService materialService, OriginService originService, PatternService patternService) {
        this.productService = productService;
        this.uploadService = uploadService;
        this.imageService = imageService;
        this.sizeService = sizeService;
        this.categoryService = categoryService;
        this.colorService = colorService;
        this.materialService = materialService;
        this.originService = originService;
        this.patternService = patternService;
    }


    @GetMapping("/admin/product")
    public String getProductPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").ascending());
        Page<Product> productPage = productService.getAllProducts(pageable);

        model.addAttribute("productPage", productPage);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getProductCreate(Model model) {
        List<Size> sizes = sizeService.getAllSizes();
        List<Color> colors = colorService.getAllColors();
        List<Category> categories = categoryService.getAllCategories();
        List<Pattern> patterns = patternService.getAllPatterns();
        List<Origin> origins = originService.getAllOrigins();
        List<Material> materials = materialService.getAllMaterials();
        model.addAttribute("sizes", sizes);
        model.addAttribute("colors", colors);
        model.addAttribute("categories", categories);
        model.addAttribute("patterns", patterns);
        model.addAttribute("origins", origins);
        model.addAttribute("materials", materials);
        model.addAttribute("newProduct", new Product());

        return "admin/product/create";
    }

    @PostMapping("/admin/product/create-product")
    public String postMethodName(@ModelAttribute("newProduct") @Valid Product product,
                                 BindingResult newBindingResult,
                                 @RequestParam("getImgFiles") MultipartFile[] files,
                                 Model model) {
        List<Image> lstImage = new ArrayList<>();

        if (this.productService.existsByName(product.getName())) {
            model.addAttribute("errorMessage", "Tên sản phẩm đã tồn tại!");
            return "admin/product/create";
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            model.addAttribute("errorMessage", "Tên sản phẩm không được để trống!");
            return "admin/product/create";
        }

        if (product.getPrice() == null) {
            model.addAttribute("errorMessage", "Giá sản phẩm không được để trống!");
            return "admin/product/create";
        }

        if (product.getQuantity() == null) {
            model.addAttribute("errorMessage", "Số lượng sản phẩm không được để trống!");
            return "admin/product/create";
        }

        String priceString = String.valueOf(product.getPrice());
        priceString = priceString.replaceAll("[^0-9.]", "");
        String regex = "^[0-9]+(\\.[0-9]+)?$";

        if (!priceString.matches(regex)) {
            model.addAttribute("errorMessage", "Giá sản phẩm phải là số hợp lệ và không chứa ký tự ngoài số và dấu chấm!");
            return "admin/product/create";
        }

        try {
            double price = Double.parseDouble(priceString);

            if (price <= 0) {
                model.addAttribute("errorMessage", "Giá sản phẩm phải lớn hơn 0!");
                return "admin/product/create";
            }

            product.setPrice(price);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Giá sản phẩm phải là số hợp lệ!");
            return "admin/product/create";
        }

        if (product.getQuantity() <= 0) {
            model.addAttribute("errorMessage", "Số lượng sản phẩm phải lớn hơn 0!");
            return "admin/product/create";
        }


        Product productNew = this.productService.handleSaveProduct(product);

        // Lưu các ảnh được tải lên
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {
                String fileExtension = Objects.requireNonNull(files[i].getOriginalFilename())
                        .substring(files[i].getOriginalFilename().lastIndexOf('.'));
                String fileName = UUID.randomUUID().toString() + fileExtension;

                Path imagePath = Paths.get("src", "main", "webapp", "resources", "images", "product", fileName);

                try {
                    if (!Files.exists(imagePath.getParent())) {
                        Files.createDirectories(imagePath.getParent());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Lưu file ảnh vào thư mục
                try {
                    Files.write(imagePath, files[i].getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Tạo đối tượng Image và gán thông tin
                Image img = new Image();
                img.setImageUrl(fileName);  // Lưu tên file vào DB
                img.setProduct(product);

                // Đánh dấu ảnh đầu tiên là avatar (ảnh chính)
                if (i == 0) {
                    img.setIsMain(true);
                } else {
                    img.setIsMain(false);
                }

                lstImage.add(img);
                this.imageService.handleSaveImage(img);  // Lưu ảnh vào DB
            }
        }

        // Gán ảnh vào sản phẩm sau khi lưu
        Product productById = this.productService.findProductById(productNew.getId());
        productById.setImages(lstImage);

        // Lưu lại sản phẩm với danh sách ảnh đã gán
        this.productService.handleSaveProduct(productById);

        return "redirect:/admin/product?success=true";
    }


    @GetMapping("/admin/product/detail/{id}")
    public String getProductDetail(@PathVariable("id") long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }


    @GetMapping("/admin/product/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);

        if (product == null) {
            model.addAttribute("error", "Sản phẩm không tồn tại.");
            return "admin/product/error";
        }

        List<Category> categories = categoryService.getAllCategories();
        List<Size> sizes = sizeService.getAllSizes();
        List<Color> colors = colorService.getAllColors();
        List<Material> materials = materialService.getAllMaterials();
        List<Pattern> patterns = patternService.getAllPatterns();
        List<Origin> origins = originService.getAllOrigins();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        model.addAttribute("sizes", sizes);
        model.addAttribute("colors", colors);
        model.addAttribute("materials", materials);
        model.addAttribute("patterns", patterns);
        model.addAttribute("origins", origins);

        return "admin/product/edit";
    }


    @RequestMapping(value = "/admin/product/edit/{id}", method = RequestMethod.POST)
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult result,
                                @RequestParam("getImgFiles") MultipartFile[] files,
                                Model model) {


        Product existingProduct = productService.findProductById(id);
        if (existingProduct == null) {
            model.addAttribute("errorMessage", "Sản phẩm không tồn tại.");
            return "admin/product/error";
        }

        try {

            List<Image> updatedImages = new ArrayList<>();

            for (int i = 0; i < files.length; i++) {
                if (!files[i].isEmpty()) {

                    String fileExtension = Objects.requireNonNull(files[i].getOriginalFilename())
                            .substring(files[i].getOriginalFilename().lastIndexOf('.'));
                    String fileName = UUID.randomUUID() + fileExtension;

                    Path imagePath = Paths.get("src", "main", "webapp", "resources", "images", "product", fileName);


                    Files.createDirectories(imagePath.getParent()); // Tạo thư mục nếu chưa tồn tại
                    Files.write(imagePath, files[i].getBytes());


                    Image image = new Image();
                    image.setImageUrl(fileName);
                    image.setProduct(existingProduct);

                    if (i == 0) {
                        image.setIsMain(true);
                    } else {
                        image.setIsMain(false);
                    }

                    updatedImages.add(image);
                }
            }


            if (!updatedImages.isEmpty()) {
                imageService.deleteImagesByProduct(existingProduct);
                for (Image newImage : updatedImages) {
                    imageService.handleSaveImage(newImage);
                }
                existingProduct.setImages(updatedImages);
            }

            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setMaterial(product.getMaterial());
            existingProduct.setPattern(product.getPattern());
            existingProduct.setOrigin(product.getOrigin());
            existingProduct.setSize(product.getSize());
            existingProduct.setColor(product.getColor());

            productService.handleSaveProduct(existingProduct);


        } catch (Exception e) {

            return "redirect:/admin/product";
        }

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/search")
    public String searchProducts(@RequestParam("productName") String productName, Model model) {
        List<Product> filteredProducts = productService.searchByName(productName);
        model.addAttribute("productPage", filteredProducts); // Gửi filteredProducts vào model với tên 'productPage'
        return "admin/product/show"; // Trả về trang JSP
    }

}




