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
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.Role;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.UserDTO;
import vn.duantn.sominamshop.model.dto.response.UserProjection;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.UploadService;
import vn.duantn.sominamshop.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("api/admin/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final UploadService uploadService;
    private final AddressService addressService;
    @GetMapping("/get/order/customers")
    public ResponseEntity<?> getCustomersForOrder(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "limit", defaultValue = "2") Integer limit,
            @RequestParam(value = "keyword", defaultValue = "") String search) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserProjection> pageCustomer = userService.findByFullNameAndRole(pageable, search, null, 2);
        return ResponseEntity.ok(pageCustomer);
    }
    @GetMapping("/get/customers")
    public ResponseEntity<?> getCustomers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "2") int limit,
            @RequestParam(value = "keyword", defaultValue = "") String search,
            @RequestParam(value = "status", defaultValue = "") Boolean status
    ) {
        System.out.println(status + " trạng thái của khách hangd");
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserProjection> pageCustomer = userService.findByFullNameAndRole(pageable, search, status, 2);
        return ResponseEntity.ok(pageCustomer);
    }
    @GetMapping("/get/staffs")
    public ResponseEntity<?> getStaff(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "2") int limit,
            @RequestParam(value = "keyword", defaultValue = "") String search
    ){
        Pageable pageable = PageRequest.of(page, limit);
        Page<UserDTO> pageStaff = userService.findStaff(pageable, search, null);
        return ResponseEntity.ok(pageStaff);
    }
    @GetMapping("/get/address/{id}")
    public ResponseEntity<?> getAddress(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAddressByIdUser(id));
    }
    @PostMapping("/save/customer")
    public ResponseEntity<?> saveCustomer(@RequestBody User user){
        System.out.println(user + " dữ liệu trả về");
        Map<String, String> validationErrors = userService.validateCustomerData(user);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validationErrors);
        }
        if(user.getPhoneNumber() != null)
            user.getAddress().forEach(address -> {address.setUser(user); address.setStatus(true);});
        user.setRole(Role.builder().id(2).build());
        user.setStatus(true);
        User userSave = userService.handleSaveUser(user);
        UserDTO userDTO = UserDTO.toDTO(userSave);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping(value = "/save/customer/multipart")
    public ResponseEntity<?> saveCustomerMultipart(
            @RequestPart(name = "user") String userJson,
            @RequestPart(name = "file", required = false) MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        User user = objectMapper.readValue(userJson, User.class);
        Map<String, String> validationErrors = userService.validateCustomerData(user);
        if (!validationErrors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(validationErrors);
        }
        if(user.getPhoneNumber() != null)
            user.getAddress().forEach(address -> {address.setUser(user); address.setStatus(true);});
            user.setRole(Role.builder().id(2).build());
        if(file != null){
            String avatar = uploadService.handleSaveAvatar(file, "/resources/images/avatar");
            user.setAvatar(avatar);
        }
        user.setStatus(true);
        User userSave = userService.handleSaveUser(user);
        UserDTO userDTO = UserDTO.toDTO(userSave);
        return ResponseEntity.ok(userDTO);
    }
    @PutMapping("/update/customer")
    public ResponseEntity<?> updateCustomer(
            @RequestPart(name = "user" ) String userJson,
            @RequestPart(name = "file", required = false) MultipartFile file) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        User user = objectMapper.readValue(userJson, User.class);
        Map<String,String> map =  userService.updateUser(user, file);
        if(!map.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
        }
        return ResponseEntity.ok("chỉnh sửa thành công");
    }
    @PutMapping("/update/status/customer")
    public ResponseEntity<?> updateStatusCustomer(
            @RequestParam(value = "id", defaultValue = "") Long id,
            @RequestParam(value = "status", defaultValue = "true") Boolean status
    ){
        userService.updateStatus(id, status);
        return ResponseEntity.ok("Chỉnh sửa thành công");
    }
    @PutMapping("/update/address")
    public ResponseEntity<?> updateAddress(@RequestBody Address address){
        addressService.updateAddress(address);
        return ResponseEntity.ok("chỉnh sủa thành công");
    }
}
