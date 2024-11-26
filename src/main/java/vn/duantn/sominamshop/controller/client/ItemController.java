package vn.duantn.sominamshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Image;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {

    private final ProductService productService;
    private final UserService userService;
    private final AddressService addressService;

    public ItemController(ProductService productService, UserService userService, AddressService addressService) {
        this.productService = productService;
        this.userService = userService;
        this.addressService = addressService;
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

    @GetMapping("/blog")
    public String getBlog() {
        return "client/blog/show";
    }

    @GetMapping("/product/{id}")
    public String getProductDetail(Model model, @PathVariable long id) {
        Product product = this.productService.findProductByIdWithImg(id);
        model.addAttribute("product", product);
        return "client/product/detail";
    }

    @GetMapping("/add-product-to-cart/{id}")
    public String addProductToCart(HttpServletRequest request,
            @PathVariable("id") long idProduct) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        this.productService.addProductToCart(email, idProduct, session);
        return "redirect:/";
    }

    @GetMapping("/remove-product-from-cart/{id}")
    public String removeProductCart(@PathVariable long id, HttpServletRequest request) {
        Product product = this.productService.findProductById(id);
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        this.productService.deteleCartDetail(email, product, session);
        return "redirect:/cart";
    }

    @GetMapping("/order")
    public String getOrder(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");

        //
        List<CartDetail> lstCartDetail = this.productService.getAllProductByUser(emailUser);
        double totalPrice = 0;
        for (CartDetail cartDetail : lstCartDetail) {
            totalPrice += cartDetail.getPrice();
        }

        //
        User user = this.userService.findUserByEmail(emailUser);
        List<Address> arrAddressByUser = this.addressService.findAllAddressByUser(user);
        for (Address address : arrAddressByUser) {
            if (address.isStatus() == true) {
                model.addAttribute("address", address);
            }
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("lstCartDetail", lstCartDetail);
        return "client/cart/checkout";
    }
}
