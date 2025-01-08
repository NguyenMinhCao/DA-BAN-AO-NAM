package vn.duantn.sominamshop.controller.client;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Promotion;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.OrderUpdateRequestDTO;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.CartService;
import vn.duantn.sominamshop.service.OrderService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.PromotionService;
import vn.duantn.sominamshop.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderControllerClient {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final PromotionService promotionService;

    public OrderControllerClient(ProductService productService, UserService userService, AddressService addressService,
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
        session.removeAttribute("checkIsStatusAddress");
        String emailUser = (String) session.getAttribute("email");

        // Lấy ra tổng tiền hàng
        List<CartDetail> lstCartDetail = this.cartService.getAllCartDetailByCart(emailUser);
        double totalPrice = 0;
        for (CartDetail cartDetail : lstCartDetail) {
            totalPrice += cartDetail.getPrice();
        }

        Order order = this.orderService.findOrderByStatusAndCreatedBy();
        double shippingPrice = 0;

        String shippingMethodString = order.getShippingMethod().toString();
        if (shippingMethodString.equals("EXPRESS")) {
            shippingPrice = 50000;
        } else if (shippingMethodString.equals("FAST")) {
            shippingPrice = 30000;
        } else {
            shippingPrice = 20000;
        }

        // Tính tổng tiền thanh toán
        double totalPayment = 0;
        totalPayment = totalPrice + shippingPrice;

        if (order.getPromotion() != null) {
            totalPayment = totalPayment - Double.parseDouble(order.getPromotion().getDiscountValue());
        }

        // Lấy địa chỉ mặc định
        User user = this.userService.findUserByEmail(emailUser);
        List<Address> arrAddressByUser = this.addressService.findAllAddressByUser(user);

        if (session.getAttribute("isChangeAddress") != null) {
            Long idAddress = order.getAddress().getId();
            Address addressById = this.addressService.findAddressById(idAddress);

            session.setAttribute("address", addressById);
        }

        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("lstCartDetail", lstCartDetail);
        session.setAttribute("totalPayment", totalPayment);
        session.setAttribute("shippingPrice", shippingPrice);
        session.setAttribute("arrAddressByUser", arrAddressByUser);

        return "client/cart/checkout";
    }

    @PostMapping("/order/update")
    public ResponseEntity<Map<String, Object>> postMethodName(@RequestBody OrderUpdateRequestDTO orderReq,
            HttpServletRequest req) {
        HttpSession session = req.getSession();

        return ResponseEntity.ok(this.orderService.orderCheckoutUpdate(orderReq, session));
    }

    @PostMapping("/order-checkout")
    public String postOrder(HttpServletRequest req) {
        HttpSession session = req.getSession();

        this.orderService.orderCheckout(session);
        return "redirect:/";
    }
}
