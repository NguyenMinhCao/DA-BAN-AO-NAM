package vn.duantn.sominamshop.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.RegisterDTO;
import vn.duantn.sominamshop.repository.AddressRepository;
import vn.duantn.sominamshop.repository.RoleRepository;
import vn.duantn.sominamshop.repository.UserRepository;

import java.io.File;
import java.io.IOException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }

    public Boolean checkEmailExits(String email) {
        User user = this.userRepository.findByEmail(email);

        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setFullName(registerDTO.getFullName());
        String hashPassWord = passwordEncoder.encode(registerDTO.getPassword());
        user.setPassword(hashPassWord);
        user.setRole(this.getRoleByName("USER"));
        return user;
    }

    public String hienthi(Model model) {
        model.addAttribute("listUser", userRepository.findAll());
        return "admin/user/user";
    }

    public boolean remove(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String addUser(User user, MultipartFile avatarFile, BindingResult bindingResult) throws Exception {
        // Kiểm tra nếu có lỗi trong bindingResult
        if (bindingResult.hasErrors()) {
            return "error"; // Trả về error nếu có lỗi trong bindingResult
        }

        // Xử lý ảnh đại diện (nếu có file ảnh)
        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                // Xử lý và lưu file ảnh đại diện
                UploadService uploadService = null;
                String avatarFileName = uploadService.handleSaveUploadFile(avatarFile, "avatars");
                user.setAvatar(avatarFileName); // Lưu tên file ảnh đại diện vào user
            } catch (Exception e) {
                // Nếu có lỗi trong quá trình xử lý ảnh, thông báo lỗi
                return "error";
            }
        }

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Set role mặc định (ví dụ: "USER")
        Role userRole = roleRepository.findByName("USER");
        user.setRole(userRole);

        // Lưu người dùng vào cơ sở dữ liệu
        try {
            userRepository.save(user);
        } catch (Exception e) {
            // Nếu có lỗi khi lưu người dùng, trả về lỗi
            return "error";
        }

        return "success"; // Trả về thành công khi lưu xong
    }
}
