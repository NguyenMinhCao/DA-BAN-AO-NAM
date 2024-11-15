package vn.duantn.sominamshop.controller.admin;

import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.service.UploadService;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final UploadService uploadService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user/create")
    public String getCreateUser(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create-user")
    public String createUser(
            @ModelAttribute("newUser") User user) {
        String hashCode = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashCode);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));
        this.userService.handleSaveUser(user);
        //
        return "redirect:/admin/user/create";
    }
}
