package vn.duantn.sominamshop.controller.client;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.Size;
import vn.duantn.sominamshop.model.dto.request.DataGetProductDetail;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.CartService;
import vn.duantn.sominamshop.service.ProductDetailService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class ItemController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final AddressService addressService;
    private final ProductDetailService productDetailService;

    public ItemController(ProductService productService, UserService userService, AddressService addressService,
            CartService cartService, ProductDetailService productDetailService) {
        this.productService = productService;
        this.userService = userService;
        this.addressService = addressService;
        this.cartService = cartService;
        this.productDetailService = productDetailService;
    }

    @GetMapping("/blog")
    public String getBlog() {
        return "client/blog/show";
    }


    @GetMapping("/product/{id}")
    public String getProductDetail(Model model, @PathVariable long id) {
        Product product = this.productService.findProductById(id);
        List<ProductDetail> lstProductDetail = this.productDetailService.findProductDetailByProducts(product);

        List<Color> lstColorInProDetail = new ArrayList<>();
        List<Size> lstSizeInProDetail = new ArrayList<>();

        Set<Long> sizeIds = new LinkedHashSet<>();
        long productIdSize = 0;
        Set<Long> colorIds = new LinkedHashSet<>(); // Sử dụng LinkedHashSet để giữ thứ tự
        long productIdColor = 0;

        for (ProductDetail productDetail : lstProductDetail) {
            Size size = productDetail.getSize();
            Color color = productDetail.getColor();
            if (!colorIds.contains(color.getId())) {
                colorIds.add(color.getId());
                lstColorInProDetail.add(color);
                productIdSize = productDetail.getProduct().getId();
            }

            if (!sizeIds.contains(size.getId())) {
                sizeIds.add(size.getId());
                lstSizeInProDetail.add(size);
                productIdColor = productDetail.getProduct().getId();
            }
        }

        Integer quantityProduct = this.productService.quantityProduct(id);

        model.addAttribute("product", product);
        model.addAttribute("productIdSize", productIdSize);
        model.addAttribute("productIdColor", productIdColor);
        model.addAttribute("lstProductDetail", lstProductDetail);
        model.addAttribute("lstColorInProDetail", lstColorInProDetail);
        model.addAttribute("lstSizeInProDetail", lstSizeInProDetail);
        model.addAttribute("quantityProduct", quantityProduct);
        return "client/product/detail";
    }

    @PostMapping("/product/product-detail-by-color")
    public ResponseEntity<List<Long>> findProductDetailByColor(@RequestBody DataGetProductDetail data) {

        return ResponseEntity.ok().body(this.productDetailService.findProductDetailByColor(data));
    }

    @PostMapping("/product/product-detail-by-size")
    public ResponseEntity<List<Long>> findProductDetailBySize(@RequestBody DataGetProductDetail data) {

        return ResponseEntity.ok().body(this.productDetailService.findProductDetailBySize(data));
    }

}
