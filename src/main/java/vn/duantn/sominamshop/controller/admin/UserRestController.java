package vn.duantn.sominamshop.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.UserDTO;
import vn.duantn.sominamshop.service.UploadService;
import vn.duantn.sominamshop.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("api/admin/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final UploadService uploadService;
    @GetMapping("/get/customers")
    public ResponseEntity<?> getCustomers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "2") int limit,
            @RequestParam(value = "keyword") String search) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserDTO> pageCustomer = userService.findByFullNameAndRole(pageable, search);
        return ResponseEntity.ok(pageCustomer);
    }
    @GetMapping("/get/staffs")
    public ResponseEntity<?> getStaff(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "2") int limit,
            @RequestParam(value = "keyword", defaultValue = " ") String search
    ){
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserDTO> pageStaff = userService.findByFullNameAndRole(pageable, search);
        return ResponseEntity.ok(pageStaff);
    }

    @PostMapping("/save/customer")
    public ResponseEntity<?> saveCustomer(@RequestBody User user){
        System.out.println(user + " dữ liệu trả về");
        Map<String, String> validationErrors = userService.validateCustomerData(user);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validationErrors);
        }
        if(user.getPhoneNumber() != null)
            user.getAddress().forEach(address -> address.setUser(user));
        user.setRole(Role.builder().id(1).build());
        User userSave = userService.handleSaveUser(user);
        UserDTO userDTO = UserDTO.toDTO(userSave);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping(value = "/save/customer/multipart")
    public ResponseEntity<?> saveCustomerMultipart(
            @RequestPart(name = "user") String userJson,
            @RequestPart(name = "file") MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        User user = objectMapper.readValue(userJson, User.class);
        Map<String, String> validationErrors = userService.validateCustomerData(user);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validationErrors);
        }
        if(user.getPhoneNumber() != null)
            user.getAddress().forEach(address -> address.setUser(user));
        user.setRole(Role.builder().id(1).build());
        String avatar = uploadService.handleSaveAvatar(file, "/resources/images/avatar");
        user.setAvatar(avatar);
        User userSave = userService.handleSaveUser(user);
        UserDTO userDTO = UserDTO.toDTO(userSave);
        return ResponseEntity.ok(userDTO);
    }
}
