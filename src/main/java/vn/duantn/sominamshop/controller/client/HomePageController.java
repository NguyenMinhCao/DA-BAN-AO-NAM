package vn.duantn.sominamshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.RegisterDTO;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;

    public HomePageController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> listProducts = productService.getAllProduct();
        model.addAttribute("listProducts", listProducts);
        return "client/homepage/show";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> listProducts = productService.getAllProduct();
        model.addAttribute("listProducts", listProducts);
        return "client/product/show";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = this.userService.registerDTOtoUser(registerDTO);
        this.userService.handleSaveUser(user);
        return "client/auth/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "client/auth/login";
    }
}
