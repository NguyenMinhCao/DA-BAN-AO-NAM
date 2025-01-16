package vn.duantn.sominamshop.controller.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.constants.DiscountType;
import vn.duantn.sominamshop.model.constants.ShippingMethod;
import vn.duantn.sominamshop.model.dto.OrderUpdateRequestDTO;
import vn.duantn.sominamshop.service.*;
//import vn.duantn.sominamshop.service.PromotionService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderControllerClient {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final CouponService promotionService;

    public OrderControllerClient(ProductService productService, UserService userService, AddressService addressService,
            CartService cartService, OrderService orderService, CouponService promotionService) {
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

        if (emailUser == null) {
            // Xử lý khi người dùng chưa đăng nhập
            // Có thể redirect đến trang đăng nhập hoặc hiển thị thông báo lỗi
            return "redirect:/login"; // Ví dụ redirect đến trang đăng nhập
        }

        // Lấy ra tổng tiền hàng từ giỏ hàng
        List<CartDetail> lstCartDetail = this.cartService.getAllCartDetailByCart(emailUser);
        BigDecimal totalPrice = lstCartDetail.stream()
                .map(cartDetail -> BigDecimal.valueOf(cartDetail.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Lấy đơn hàng hiện tại
        Order order = this.orderService.findOrderByStatusAndCreatedBy();

        if (order == null) {
            // Xử lý khi không tìm thấy đơn hàng
            // Có thể tạo đơn hàng mới hoặc hiển thị thông báo lỗi
            return "redirect:/cart"; // Ví dụ redirect đến trang giỏ hàng
        }

        // Xác định phí vận chuyển dựa trên phương thức vận chuyển
        BigDecimal shippingPrice;
        ShippingMethod shippingMethodEnum = order.getShippingMethod();
        switch (shippingMethodEnum) {
            case EXPRESS:
                shippingPrice = BigDecimal.valueOf(50000);
                break;
            case FAST:
                shippingPrice = BigDecimal.valueOf(30000);
                break;
            default:
                shippingPrice = BigDecimal.valueOf(20000);
                break;
        }

        // Tính tổng thanh toán trước giảm giá
        BigDecimal totalPayment = totalPrice.add(shippingPrice);

        // Khởi tạo biến giảm giá
        BigDecimal discountValue = BigDecimal.ZERO;

        // Áp dụng coupon nếu có
        Coupon coupon = order.getCoupon();
        if (coupon != null) {
            // Kiểm tra tính hợp lệ của coupon
            boolean isValidCoupon = coupon.getEndDate() != null &&
                    (coupon.getEndDate().isAfter(LocalDateTime.now())
                            || coupon.getEndDate().isEqual(LocalDateTime.now()))
                    &&
                    Boolean.TRUE.equals(coupon.getStatus());

            if (isValidCoupon) {
                // Kiểm tra nếu tổng thanh toán đạt tối thiểu để áp dụng mã giảm giá
                BigDecimal minimumValue = BigDecimal.valueOf(coupon.getMinimumValue());
                if (totalPayment.compareTo(minimumValue) >= 0) {
                    if (coupon.getDiscountType() == DiscountType.FIXED) {
                        if (coupon.getDiscountValueFixed() != null) {
                            discountValue = BigDecimal.valueOf(coupon.getDiscountValueFixed());
                        }
                    } else if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
                        if (coupon.getDiscountValuePercent() != null) {
                            BigDecimal percent = BigDecimal.valueOf(coupon.getDiscountValuePercent())
                                    .divide(BigDecimal.valueOf(100));
                            discountValue = totalPayment.multiply(percent);
                            // Áp dụng mức giảm tối đa nếu có
                            if (coupon.getMaximumReduction() != null) {
                                discountValue = discountValue.min(BigDecimal.valueOf(coupon.getMaximumReduction()));
                            }
                        }
                    }
                    // Đảm bảo giảm giá không vượt quá tổng thanh toán
                    if (discountValue.compareTo(totalPayment) > 0) {
                        discountValue = totalPayment;
                    }
                    // Trừ giảm giá từ tổng thanh toán
                    totalPayment = totalPayment.subtract(discountValue);
                } else {
                    // Nếu không đạt tối thiểu, không áp dụng giảm giá và có thể hiển thị thông báo
                    discountValue = BigDecimal.ZERO;
                    model.addAttribute("couponMessage", "Đơn hàng không đủ giá trị tối thiểu để áp dụng mã giảm giá.");
                }
            } else {
                // Nếu coupon không hợp lệ, không áp dụng giảm giá và có thể hiển thị thông báo
                discountValue = BigDecimal.ZERO;
                model.addAttribute("couponMessage", "Mã giảm giá không hợp lệ hoặc đã hết hạn.");
            }
        }

        // Lấy địa chỉ mặc định của người dùng
        User user = this.userService.findUserByEmail(emailUser);
        List<Address> arrAddressByUser = this.addressService.findAllAddressByUser(user);

        // Nếu người dùng đã thay đổi địa chỉ, lấy địa chỉ mới
        if (session.getAttribute("isChangeAddress") != null) {
            Long idAddress = order.getAddress().getId();
            Address addressById = this.addressService.findAddressById(idAddress);
            if (addressById != null) {
                session.setAttribute("address", addressById);
            }
        }

        // Cập nhật session với các giá trị tính toán
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("lstCartDetail", lstCartDetail);
        session.setAttribute("totalPayment", totalPayment);
        session.setAttribute("shippingPrice", shippingPrice);
        session.setAttribute("arrAddressByUser", arrAddressByUser);

        // Cập nhật model với các giá trị để hiển thị trên giao diện
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("shippingPrice", shippingPrice);
        model.addAttribute("totalPayment", totalPayment);
        model.addAttribute("discountValue", discountValue);
        model.addAttribute("lstCartDetail", lstCartDetail);
        model.addAttribute("arrAddressByUser", arrAddressByUser);
        if (coupon != null) {
            model.addAttribute("coupon", coupon);
        }

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
