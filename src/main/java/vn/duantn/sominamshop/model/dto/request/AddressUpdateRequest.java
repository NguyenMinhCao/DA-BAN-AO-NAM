package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressUpdateRequest {
    private long idOrder;
    private String fullName;
    private String phoneNumber;
    private String ward;
    private String district;
    private String city;
    private String streetDetails;
}
