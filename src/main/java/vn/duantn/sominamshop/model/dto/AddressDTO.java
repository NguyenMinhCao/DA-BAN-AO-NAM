package vn.duantn.sominamshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private long id;

    private String fullName;

    private String phoneNumber;

    private String address;

    private boolean status;
}