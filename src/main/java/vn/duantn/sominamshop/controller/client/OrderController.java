package vn.duantn.sominamshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Promotion;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.OrderCheckoutDTO;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.CartService;
import vn.duantn.sominamshop.service.OrderService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.PromotionService;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class OrderController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final PromotionService promotionService;

    public OrderController(ProductService productService, UserService userService, AddressService addressService,
            CartService cartService, OrderService orderService, PromotionService promotionService) {
        this.productService = productService;
        this.userService = userService;
        this.addressService = addressService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.promotionService = promotionService;
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

        List<Promotion> listPromotions = this.promotionService.findAllPromotion();
        OrderCheckoutDTO orderCheckout = new OrderCheckoutDTO();

        model.addAttribute("orderCheckout", orderCheckout);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("lstCartDetail", lstCartDetail);
        model.addAttribute("listPromotions", listPromotions);
        return "client/cart/checkout";
    }

    @PostMapping("/order-checkout")
    public String postOrder(@ModelAttribute("orderCheckout") OrderCheckoutDTO order, HttpServletRequest req) {
        HttpSession session = req.getSession();

        this.orderService.orderCheckout(session, order);
        return "redirect:/";
    }
}
