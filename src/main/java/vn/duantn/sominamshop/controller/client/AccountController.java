package vn.duantn.sominamshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile")
    public String getViewAccountManage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");

        User userByEmail = this.userService.findUserByEmail(emailUser);
        model.addAttribute("userByEmail", userByEmail);
        return "client/account/account";
    }

    @PostMapping("/user/account-update")
    public String updateAccount(@ModelAttribute User user, @RequestParam("getImgFile") MultipartFile file) {

        return "redirect:/user/account";
    }

    @GetMapping("/user/address")
    public String getViewAddress(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");

        User userByEmail = this.userService.findUserByEmail(emailUser);
        model.addAttribute("userByEmail", userByEmail);
        return "client/account/address";
    }

}
