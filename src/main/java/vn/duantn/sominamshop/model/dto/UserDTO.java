package vn.duantn.sominamshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String email;

    private String fullName;

    private String phoneNumber;

    private String avatar;

    private Boolean gender;
    private List<AddressDTO> address;

    public static UserDTO toDTO(User user){
        List<AddressDTO> addressDTOs = user.getAddress()
                .stream()
                .map(a -> new AddressDTO(a.getId(), a.getFullName(), a.getPhoneNumber() ,a.getStreetDetails(), a.isStatus()))
                .collect(Collectors.toList());
        return new UserDTO(user.getId(), user.getEmail(), user.getFullName(), user.getPhoneNumber(), user.getAvatar(), user.getGender(), addressDTOs);
    }
}
