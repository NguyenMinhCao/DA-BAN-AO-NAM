package vn.duantn.sominamshop.controller.admin;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.repository.AddressRepository;
import vn.duantn.sominamshop.repository.RoleRepository;
import vn.duantn.sominamshop.repository.UserRepository;
import vn.duantn.sominamshop.service.UploadService;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private RoleRepository roleRepository;

    public UserController(UserService userService, UserRepository userRepository, UploadService uploadService,
                          PasswordEncoder passwordEncoder, AddressRepository addressRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
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
        return "redirect:/admin/user/create";
    }

    @GetMapping("/admin/user/hien-thi")
    public String hienthi(Model model) {
        return userService.hienthi(model);
    }

    @PostMapping("/admin/user/remove")
    public String remove(@RequestParam("id") long id, RedirectAttributes redirectAttributes) {
        if (userService.remove(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Xóa người dùng thành công.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy người dùng.");
        }
        return "redirect:/admin/User/hien-thi";
    }

    @GetMapping("/admin/user/viewadd")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleRepository.findAll());
        model.addAttribute("listsAddress", addressRepository.findAll());
        return "admin/user/addUser";
    }

    @PostMapping("/admin/user/add")
    public String addUser(@ModelAttribute User user,
                          @RequestParam("avatarFile") MultipartFile avatarFile,
                          BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        // Nếu có lỗi trong việc xác thực, quay lại form và hiển thị lỗi
        if (bindingResult.hasErrors()) {
            model.addAttribute("listRoles", roleRepository.findAll()); // Thêm danh sách vai trò vào model
            model.addAttribute("listsAddress", addressRepository.findAll()); // Thêm danh sách địa chỉ vào model
            return "admin/user/addUser"; // Quay lại trang thêm người dùng
        }

        try {
            // Gọi đến phương thức service để xử lý logic thêm người dùng
            String result = userService.addUser(user, avatarFile, bindingResult);

            if (result.equals("success")) {
                redirectAttributes.addFlashAttribute("successMessage", "Thêm người dùng thành công.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi thêm người dùng.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi xử lý dữ liệu người dùng.");
        }

        return "redirect:/admin/user/hien-thi"; // Redirect tới trang hiển thị danh sách người dùng
    }
}
