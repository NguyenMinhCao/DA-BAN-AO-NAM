package vn.duantn.sominamshop.controller.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.tags.shaded.org.apache.regexp.recompile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.Order;
import vn.duantn.sominamshop.model.OrderDetail;
import vn.duantn.sominamshop.model.Coupon;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.OrderService;
import vn.duantn.sominamshop.service.CouponService;
import vn.duantn.sominamshop.service.UploadService;
import vn.duantn.sominamshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AccountController {

    private final UserService userService;
    private final OrderService orderService;
    private final AddressService addressService;
    private final UploadService uploadService;

    public AccountController(UserService userService, OrderService orderService, AddressService addressService,
            UploadService uploadService) {
        this.userService = userService;
        this.orderService = orderService;
        this.addressService = addressService;
        this.uploadService = uploadService;
    }

    @GetMapping("/user/profile")
    public String getViewAccountManage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");

        User userByEmail = this.userService.findUserByEmail(emailUser);
        int uu = userByEmail.getDateOfBirth().getMonthValue();
        System.out.println(uu);
        model.addAttribute("userByEmail", userByEmail);
        return "client/account/account";
    }

    @PostMapping("/user/account-update")
    public String updateAccount(@ModelAttribute("userByEmail") User user,
            @RequestParam("dateOfBirth") String dateOfBirthStr,
            @RequestParam("getImgFile") MultipartFile file,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        String avatar = this.uploadService.handleSaveUploadFile(file, "avatar");
        this.userService.handleUpdateUser(user, session, dateOfBirthStr, avatar);
        return "redirect:/user/profile";
    }

    @GetMapping("/user/address")
    public String getViewAddress(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");

        User userByEmail = this.userService.findUserByEmail(emailUser);
        User user = this.userService.findUserByEmail(emailUser);
        List<Address> arrAddressByUser = this.addressService.findAllAddressByUser(user);

        model.addAttribute("arrAddressByUser", arrAddressByUser);
        model.addAttribute("userByEmail", userByEmail);
        return "client/account/address";
    }

    @GetMapping("/user/orders")
    public String getOrderShow(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");

        User userByEmail = this.userService.findUserByEmail(emailUser);
        List<Order> orderByUsers = this.orderService.findOrderByUser(userByEmail);

        model.addAttribute("orderUsers", orderByUsers);
        // model.addAttribute("orderUsers", orderByUsers);
        model.addAttribute("userByEmail", userByEmail);

        return "client/account/order-show";
    }

    @GetMapping("/user/change-pass")
    public String getViewChangePass(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        return "client/account/change-pass";
    }
}
