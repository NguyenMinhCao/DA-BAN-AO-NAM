package vn.duantn.sominamshop.model.dto;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;
import vn.duantn.sominamshop.model.Address;
import vn.duantn.sominamshop.model.Cart;
import vn.duantn.sominamshop.model.Role;

import java.time.LocalDate;
import java.util.List;

public class UserRequestDTO {
    private long id;
    private String email;

    private String password;

    private String fullName;

    private String phoneNumber;

    private MultipartFile avatar;

    private Boolean gender;

    private LocalDate dateOfBirth;

    private String status;

    private Cart cart;

    private Role role;

    private List<Address> address;
}
