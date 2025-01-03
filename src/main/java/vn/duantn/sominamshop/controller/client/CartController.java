package vn.duantn.sominamshop.controller.client;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.Promotion;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.CartDetailUpdateRequestDTO;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.CartService;
import vn.duantn.sominamshop.service.OrderService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.PromotionService;
import vn.duantn.sominamshop.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final AddressService addressService;
    private final PromotionService promotionService;
    private final OrderService orderService;

    public CartController(ProductService productService, UserService userService, AddressService addressService,
            CartService cartService, PromotionService promotionService, OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.addressService = addressService;
        this.cartService = cartService;
        this.promotionService = promotionService;
        this.orderService = orderService;
    }

    @GetMapping("/cart")
    public String getCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("promotionInOrder");
        session.removeAttribute("isChangeAddress");
        String emailUser = (String) session.getAttribute("email");

        List<CartDetail> lstCartDetail = this.cartService.getAllCartDetailByCart(emailUser);
        double totalPrice = 0;
        for (CartDetail cartDetail : lstCartDetail) {
            totalPrice += cartDetail.getPrice();
        }

        // Lấy ra toàn bộ promotions để hiển thị
        List<Promotion> listPromotions = this.promotionService.findAllPromotion();

        // thiết lập lại trường promotion trong order có delivery-status null khi người dùng rời
        // khỏi trang order
        Order order = this.orderService.findOrderByStatusAndCreatedBy();
        if (order != null) {
            order.setPromotion(null);
            this.orderService.saveOrder(order);
        }

        // Lấy địa chỉ mặc định phục vụ hiển thị bên /order
        User user = this.userService.findUserByEmail(emailUser);
        List<Address> arrAddressByUser = this.addressService.findAllAddressByUser(user);
        for (Address address : arrAddressByUser) {
            if (address.isStatus() == true) {
                session.setAttribute("address", address);
            }
        }

        // thiết lập mặc định tự chọn radio button ở giao diện khi chuyển vào trang
        // /cart hoặc /order
        session.setAttribute("shippingMethodInOrder", order != null ? order.getShippingMethod() : null);

        double shippingPrice = 0;
        String shippingMethodString = order.getShippingMethod().toString();

        if (order != null && shippingMethodString.equals("EXPRESS")) {
            shippingPrice = 50000;
        } else if (order != null && shippingMethodString.equals("FAST")) {
            shippingPrice = 30000;
        } else {
            shippingPrice = 20000;
        }

        double totalPayment = 0;
        totalPayment = totalPrice + shippingPrice;

        session.setAttribute("totalPayment", totalPayment);
        session.setAttribute("shippingPrice", shippingPrice);
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("lstCartDetail", lstCartDetail);
        session.setAttribute("listPromotions", listPromotions);
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

    @PutMapping("/cart/update")
    public ResponseEntity<Map<String, Object>> putMethodName(@RequestBody CartDetailUpdateRequestDTO dto,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        return ResponseEntity.ok().body(this.cartService.updateCartDetailProductQuantity(dto, session));
    }
}
