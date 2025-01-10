package vn.duantn.sominamshop.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataUpdateUserOrderDTO {
    private String emailUser;
    private String phoneNumber;
    private String oldEmailUser;
}
