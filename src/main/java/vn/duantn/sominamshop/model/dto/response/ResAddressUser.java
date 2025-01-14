package vn.duantn.sominamshop.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResAddressUser {
    private String fullName;
    private String phoneNumber;
    private String streetDetails;
    private String ward;
    private String district;
    private String city;
    private long id;
}
