package vn.duantn.sominamshop.controller.client;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.User;
import vn.duantn.sominamshop.model.dto.AddressDTO;
import vn.duantn.sominamshop.service.AddressService;
import vn.duantn.sominamshop.service.UserService;

@Controller
public class AddressController {
    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping("/user/address")
    public ResponseEntity<Void> addAddress(@RequestBody AddressDTO dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");
        User user = this.userService.findUserByEmail(emailUser);

        this.addressService.handleAddAddress(dto, user);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/user/address-detail")
    public ResponseEntity<AddressDTO> detailUpdateAddress(String idAddress, HttpServletRequest request) {

        return ResponseEntity.ok().body(this.addressService.convertAddressToAddressDTO(idAddress));
    }

    @PutMapping("/user/address")
    public ResponseEntity<Void> updateAddress(@RequestBody AddressDTO dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailUser = (String) session.getAttribute("email");
        User user = this.userService.findUserByEmail(emailUser);

        this.addressService.updateAddress(dto, user);
        return ResponseEntity.ok().body(null);
    }

}