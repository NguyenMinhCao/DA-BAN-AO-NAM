package vn.duantn.sominamshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.service.ProductService;

@Controller
public class DasboardController {

    private final ProductService productService;

    public DasboardController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String getMethodName(Model model) {
        List<Product> listProducts = productService.getAllProduct();
        model.addAttribute("listProducts", listProducts);
        return "admin/product/show";
    }

}