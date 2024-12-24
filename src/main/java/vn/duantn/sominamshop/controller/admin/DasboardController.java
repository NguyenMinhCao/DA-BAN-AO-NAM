package vn.duantn.sominamshop.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.service.ProductService;

@Controller
public class DasboardController {

    private final ProductService productService;

    public DasboardController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String getProductPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").ascending());
        Page<Product> productPage = productService.getAllProducts(pageable);

        model.addAttribute("productPage", productPage);
        return "admin/product/show";
    }

}