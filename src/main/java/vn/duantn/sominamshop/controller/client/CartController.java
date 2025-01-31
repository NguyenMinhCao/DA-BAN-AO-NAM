package vn.duantn.sominamshop.controller.client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.validator.cfg.defs.EANDef;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.CartDetail;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.Product;
import vn.duantn.sominamshop.model.ProductDetail;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.CartDetailUpdateRequestDTO;
import vn.duantn.sominamshop.model.dto.request.DataGetProductDetail;
import vn.duantn.sominamshop.model.dto.response.ResCouponDTO;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.CartService;
import vn.duantn.sominamshop.service.OrderService;
import vn.duantn.sominamshop.service.ProductDetailService;
import vn.duantn.sominamshop.service.ProductService;
import vn.duantn.sominamshop.service.CouponService;
import vn.duantn.sominamshop.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CartController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final AddressService addressService;
    private final CouponService promotionService;
    private final OrderService orderService;
    private final ProductDetailService productDetailService;

    public CartController(ProductService productService, UserService userService, AddressService addressService,
            CartService cartService, CouponService promotionService, OrderService orderService,
            ProductDetailService productDetailService) {
        this.productService = productService;
        this.userService = userService;
        this.addressService = addressService;
        this.cartService = cartService;
        this.promotionService = promotionService;
        this.orderService = orderService;
        this.productDetailService = productDetailService;
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

        // Giả sử đây là trong một phương thức của controller hoặc service
        List<Coupon> listCouponDis = this.promotionService.findAllCouponsOrderByDiscountsDesc();
        List<ResCouponDTO> listCouponDTOs = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Định nghĩa formatter một lần duy nhất
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Coupon coupon : listCouponDis) {
            // Kiểm tra trạng thái và ngày kết thúc
            if (Boolean.TRUE.equals(coupon.getStatus()) && coupon.getEndDate() != null
                    && (coupon.getEndDate().isAfter(currentDateTime) || coupon.getEndDate().isEqual(currentDateTime))) {

                ResCouponDTO res = new ResCouponDTO();
                res.setId(coupon.getId());
                res.setDiscountType(coupon.getDiscountType());
                res.setDiscountValueFixed(coupon.getDiscountValueFixed());
                res.setCouponCode(coupon.getCouponCode());
                res.setDiscountValuePercent(coupon.getDiscountValuePercent());
                res.setMaximumReduction(coupon.getMaximumReduction());
                res.setMinimumValue(coupon.getMinimumValue());
                res.setStatus(coupon.getStatus());

                // Định dạng ngày và thiết lập vào DTO
                String formattedEndDate = coupon.getEndDate().format(formatter);
                res.setEndDate(formattedEndDate);

                listCouponDTOs.add(res);
            }
        }

        // thiết lập lại trường promotion trong order có delivery-status null khi người
        // dùng rời
        // khỏi trang order
        Order order = this.orderService.findOrderByStatusAndCreatedBy();
        if (order != null) {
            order.setCoupon(null);
            this.orderService.saveOrder(order);
        }

        // Lấy địa chỉ mặc định phục vụ hiển thị bên /order
        User user = this.userService.findUserByEmail(emailUser);
        List<Address> arrAddressByUser = this.addressService.findAllAddressByUser(user);
        if (arrAddressByUser.size() > 0) {
            for (Address address : arrAddressByUser) {
                if (address.getStatus() == true) {
                    session.setAttribute("address", address);
                }
            }
        }

        // thiết lập mặc định tự chọn radio button ở giao diện khi chuyển vào trang
        // /cart hoặc /order
        session.setAttribute("shippingMethodInOrder", order != null ? order.getShippingMethod() : null);

        double shippingPrice = 0;
        String shippingMethodString = "";
        if (order != null) {
            shippingMethodString = order.getShippingMethod().toString();
        }

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
        session.setAttribute("listCouponDTOs", listCouponDTOs);
        return "client/cart/show";
    }

    @PostMapping("/add-product-to-cart")
    public ResponseEntity<Boolean> addProductToCart(HttpServletRequest request,
            @RequestBody DataGetProductDetail data) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        this.cartService.addProductDetailToCart(email, data, session);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/remove-product-from-cart/{id}")
    public String removeProductCart(@PathVariable long id, HttpServletRequest request) {
        Optional<ProductDetail> productDetail = this.productDetailService.findProductDetailById(id);
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (productDetail.isPresent()) {
            this.cartService.deleteCartDetailByCartAndProduct(email, productDetail.get(), session);
        }
        return "redirect:/cart";
    }

    @PutMapping("/cart/update")
    public ResponseEntity<Map<String, Object>> updateCart(@RequestBody CartDetailUpdateRequestDTO dto,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        return ResponseEntity.ok().body(this.cartService.updateCartDetailProductQuantity(dto, session));
    }
}
