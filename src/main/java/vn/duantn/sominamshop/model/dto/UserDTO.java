package vn.duantn.sominamshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    // private List<AddressDTO> address;
    private long id;
    private String email;

    private String fullName;

    private String phoneNumber;
    private LocalDate dateOfBirth;

    private String avatar;

    private Boolean gender;
    private Boolean status;
    // private List<AddressDTO> address;

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getEmail(), user.getFullName(), user.getPhoneNumber(),
                user.getDateOfBirth(), user.getAvatar(), user.getGender(), user.getStatus());
    }
}
