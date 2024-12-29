package vn.duantn.sominamshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.RegisterDTO;
import vn.duantn.sominamshop.model.dto.UserDTO;
import vn.duantn.sominamshop.repository.RoleRepository;
import vn.duantn.sominamshop.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Page<UserDTO> findByFullNameAndRole(Pageable pageable, String name) {
        Page<User> pageCustomer = userRepository.findByFullNameContainingAndRole(name, Role.builder().id(1).build(),
                pageable);
        Page<UserDTO> pageCustomerDto = pageCustomer.map(user -> UserDTO.toDTO(user));
        return pageCustomerDto;
    }

    public Map<String, String> validateCustomerData(User user) {
        Map<String, String> errors = new HashMap<>();
        if (user.getPhoneNumber() != null && userRepository.existsByPhoneNumber(user.getPhoneNumber())){
            errors.put("phoneNumber", "Phone number already exists.");
        }
        if (user.getEmail() != null && this.checkEmailExits(user.getEmail())) {
            errors.put("email", "Email already exists.");
        }
        return errors;
    }
}
