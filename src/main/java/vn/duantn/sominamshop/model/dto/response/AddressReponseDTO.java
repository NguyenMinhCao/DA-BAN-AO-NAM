package vn.duantn.sominamshop.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.duantn.sominamshop.model.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressReponseDTO {
    private long id;
    private String fullName;
    private String phoneNumber;
    private String ward;
    private String district;
    private String city;
    private String streetDetails;
    private boolean status;
    private Long userId;
    public static AddressReponseDTO toDTO(Address address){
        if(address != null){
            return AddressReponseDTO.builder()
                    .id(address.getId())
                    .city(address.getCity())
                    .district(address.getDistrict())
                    .ward(address.getWard())
                    .fullName(address.getFullName())
                    .phoneNumber(address.getPhoneNumber())
                    .status(address.getStatus())
                    .streetDetails(address.getStreetDetails())
                    .userId(address.getUser().getId())
                    .build();
        }
        return null;
    }
}
