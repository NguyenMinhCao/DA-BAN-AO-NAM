package vn.duantn.sominamshop.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private long idAddress;

    private String fullName;

    private String phoneNumber;

    private String streetDetails;

    private boolean status;
}
