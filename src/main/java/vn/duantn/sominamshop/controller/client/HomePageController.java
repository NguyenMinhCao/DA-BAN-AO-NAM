package vn.duantn.sominamshop.controller.client;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

import vn.duantn.sominamshop.model.Color;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.Size;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.RegisterDTO;
import vn.duantn.sominamshop.service.ColorService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.SizeService;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class HomePageController {

    private final ProductService productService;
    private final UserService userService;
    private final ColorService colorService;
    private final SizeService sizeService;

    public HomePageController(ProductService productService, UserService userService, ColorService colorService,
            SizeService sizeService) {
        this.productService = productService;
        this.userService = userService;
        this.colorService = colorService;
        this.sizeService = sizeService;
    }

    @GetMapping("/")
    public String getHomePage(@RequestParam(defaultValue = "0") int page, Model model) {

        List<Product> productPage = this.productService.getAllProduct();
        List<Color> colorList = this.colorService.getAllColors();
        List<Size> sizeList = this.sizeService.getAllSizes();
        model.addAttribute("listProducts", productPage);
        model.addAttribute("colorList", colorList);
        model.addAttribute("sizeList", sizeList);
        return "client/homepage/show";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> productPage = this.productService.getAllProduct();

        model.addAttribute("listProducts", productPage);
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
