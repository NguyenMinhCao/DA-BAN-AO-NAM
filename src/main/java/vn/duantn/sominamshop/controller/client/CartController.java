package vn.duantn.sominamshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.CartService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class CartController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final AddressService addressService;

    public CartController(ProductService productService, UserService userService, AddressService addressService,
            CartService cartService) {
        this.productService = productService;
        this.userService = userService;
        this.addressService = addressService;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String getCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");
        List<CartDetail> lstCartDetail = this.productService.getAllProductByUser(emailUser);
        double totalPrice = 0;
        for (CartDetail cartDetail : lstCartDetail) {
            totalPrice += cartDetail.getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("lstCartDetail", lstCartDetail);
        return "client/cart/show";
    }

    @GetMapping("/add-product-to-cart/{id}")
    public String addProductToCart(HttpServletRequest request,
            @PathVariable("id") long idProduct) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        this.cartService.addProductToCart(email, idProduct, session);
        return "redirect:/";
    }

    @GetMapping("/remove-product-from-cart/{id}")
    public String removeProductCart(@PathVariable long id, HttpServletRequest request) {
        Product product = this.productService.findProductById(id);
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        this.cartService.deleteCartDetailByCartAndProduct(email, product, session);
        return "redirect:/cart";
    }
}
