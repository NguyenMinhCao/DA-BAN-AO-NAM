package vn.duantn.sominamshop.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataUpdateUserOrderDTO {
    private String emailUser;
    private String phoneNumber;
    private String oldEmailUser;
}
