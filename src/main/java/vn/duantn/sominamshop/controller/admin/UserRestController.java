package vn.duantn.sominamshop.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.UserDTO;
import vn.duantn.sominamshop.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("api/admin/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    @PostMapping("/save/customer")
    public ResponseEntity<?> saveCustomer(@RequestBody User user){
        System.out.println(user + " dữ liệu trả về");
        Map<String, String> validationErrors = userService.validateCustomerData(user);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validationErrors);
        }
        user.setRole(Role.builder().id(1).build());
        User userSave = userService.handleSaveUser(user);
        UserDTO userDTO = UserDTO.toDTO(userSave);
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("/get/customers")
    public ResponseEntity<?> getCustomers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "2") int limit,
            @RequestParam(value = "keyword") String search) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserDTO> pageCustomer = userService.findByFullNameAndRole(search ,Role.builder().id(1).build(), pageable);
        return ResponseEntity.ok(pageCustomer);
    }
    @GetMapping("/get/staffs")
    public ResponseEntity<?> getStaff(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "2") int limit,
            @RequestParam(value = "keyword") String search
    ){
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserDTO> pageStaff = userService.findByFullNameAndRole(search ,Role.builder().id(1).build(), pageable);
        return ResponseEntity.ok(pageStaff);
    }
}
