package vn.duantn.sominamshop.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.RegisterDTO;
import vn.duantn.sominamshop.model.dto.UserDTO;
import vn.duantn.sominamshop.model.dto.request.DataUpdateUserOrderDTO;
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



    public void updateUserInOrder(DataUpdateUserOrderDTO dto) {
        User userByEmail = this.findUserByEmail(dto.getOldEmailUser());
        if (userByEmail != null) {
            userByEmail.setEmail(dto.getEmailUser());
            userByEmail.setPhoneNumber(dto.getPhoneNumber());
            this.userRepository.save(userByEmail);
        }
    }

    public Map<String, String> validateCustomerData(User user) {
        Map<String, String> errors = new HashMap<>();
        if (!user.getPhoneNumber().isEmpty() && userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            errors.put("phoneNumber", "Phone number already exists.");
        }
        if (!user.getEmail().isEmpty() && this.checkEmailExits(user.getEmail())) {
            errors.put("email", "Email already exists.");
        }
        return errors;
    }

    public Optional<User> findUserById(Long id) {
        return this.userRepository.findById(id);
    }

    public void handleUpdateUser(User user, HttpSession session, String dateOfBirthStr, String avatar) {
        User userById = this.userRepository.findById(user.getId()).get();

        if (userById != null) {
            session.setAttribute("email", user.getEmail());
            userById.setFullName(user.getFullName());
            userById.setEmail(user.getEmail());
            userById.setPhoneNumber(user.getPhoneNumber());
            userById.setGender(user.getGender());
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthStr);
            userById.setDateOfBirth(dateOfBirth);
            userById.setAvatar(avatar);
            this.userRepository.save(userById);
        }
    }


    public Page<UserDTO> findByFullNameAndRole(Pageable pageable, String name) {
        Page<User> pageCustomer = userRepository.findByFullNameContainingAndRole(name,
                pageable);
        Page<UserDTO> pageCustomerDto = pageCustomer.map(user -> UserDTO.toDTO(user));
        return pageCustomerDto;
    }


    public List<User> findUserByPhone(String phone) {
        return this.userRepository.findByPhoneNumberStartingWith(phone);
    }

}
