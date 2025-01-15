package vn.duantn.sominamshop.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.RegisterDTO;
import vn.duantn.sominamshop.model.dto.UserDTO;
import vn.duantn.sominamshop.model.dto.request.DataUpdateUserOrderDTO;
import vn.duantn.sominamshop.model.dto.request.EmailRequest;
import vn.duantn.sominamshop.repository.OrderRepository;
import vn.duantn.sominamshop.repository.RoleRepository;
import vn.duantn.sominamshop.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UploadService uploadService;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UploadService uploadService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.uploadService = uploadService;
    }

    public User findUserByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        return user;
    }

    public User findUserByEmailRequest(EmailRequest emailR) {
        User user = this.userRepository.findByEmail(emailR.getEmail());
        return user;
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
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
        System.out.println(user.getPhoneNumber() + " số điện thoại");
        Map<String, String> errors = new HashMap<>();
        if (user.getPhoneNumber() != null && userRepository.existsByPhoneNumberAndRole(user.getPhoneNumber(), Role.builder().id(2).build())) {
            errors.put("phoneNumber", "Số điện thoại đã tồn tại");
            return errors;
        }
        if (user.getEmail() != null && userRepository.existsByEmailAndRole(user.getEmail(), Role.builder().id(2).build())) {
            errors.put("email", "Email đã tồn tại");
            return errors;
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

    public Page<UserDTO> findByFullNameAndRole(Pageable pageable, String name, Boolean status, Integer idRole) {
        Page<User> pageCustomer = userRepository.findByFullNameContainingAndRole(name, pageable, status, idRole);
        Page<UserDTO> pageCustomerDto = pageCustomer.map(user -> UserDTO.toDTO(user));
        return pageCustomerDto;
    }

    public List<User> findUserByPhone(String phone) {
        return this.userRepository.findByPhoneNumberStartingWith(phone);
    }
    public Map<String, String> updateUser(User user, MultipartFile file){
        Map<String, String> validationErrors = new HashMap<>();
        User userUpdate = userRepository.findById(user.getId()).orElse(null);
        if(userUpdate == null){
            userUpdate = new User();
            userUpdate.setStatus(true);
            userUpdate.setRole(Role.builder().id(3).build());
        }
        userUpdate.setFullName(user.getFullName());
        if(user.getDateOfBirth() != null){
            userUpdate.setDateOfBirth(user.getDateOfBirth());
        }
        if(user.getPhoneNumber() != null){
            userUpdate.setPhoneNumber(user.getPhoneNumber());
        }
        if(user.getEmail() != null){
            userUpdate.setEmail(user.getEmail());
            System.out.println(userUpdate.getEmail() + " email");
        }
        validationErrors = this.validateCustomerData(user);
        if(!validationErrors.isEmpty()){
            return validationErrors;
        }
        userUpdate.setGender(user.getGender());
        if(file != null){
            uploadService.deleteFile(userUpdate.getAvatar(),"/resources/images/avatar");
            String avatar = uploadService.handleSaveAvatar(file, "/resources/images/avatar");
            userUpdate.setAvatar(avatar);
        }
        if(user.getPassword() != null){
            String hashPassWord = passwordEncoder.encode(user.getPassword());
            userUpdate.setPassword(hashPassWord);
        }
        userRepository.save(userUpdate);
        return validationErrors;
    }
    @Transactional
    public void updateStatus(Long id, Boolean status){
        User userUpdate = userRepository.findById(id).orElse(null);
        if(userUpdate != null){
            userUpdate.setStatus(status);
            userRepository.save(userUpdate);
        }
    }
    public Page<UserDTO> findStaff(Pageable pageable, String name, Boolean status){
        Page<User> users = userRepository.findStaff(name, pageable, status);
        Page<UserDTO> userDTOPage = users.map(UserDTO :: toDTO);
        return userDTOPage;
    }
}
