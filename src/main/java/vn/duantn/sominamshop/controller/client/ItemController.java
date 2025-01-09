package vn.duantn.sominamshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
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

//    @GetMapping("/product/{id}")
//    public String getProductDetail(Model model, @PathVariable long id) {
//        Product product = this.productService.findProductByIdWithImg(id);
//        model.addAttribute("product", product);
//        return "client/product/detail";
//    }

}
