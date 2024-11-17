package vn.duantn.sominamshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/account")
    public String getViewAccountManage(Model model, HttpServletRequest request) {
        // User user = new User();
        // model.addAttribute("userUpdate", user);
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");

        User userByEmail = this.userService.findUserByEmail(emailUser);
        model.addAttribute("userUpdate", userByEmail);
        return "client/account/account";
    }

}
