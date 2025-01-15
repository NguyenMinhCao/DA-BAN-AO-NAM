package vn.duantn.sominamshop.model.dto.response;

import java.time.LocalDate;

public interface UserProjection {
    Long getId();
    String getEmail();
    String getFullName();
    String getPhoneNumber();
    LocalDate getDateOfBirth();
    String getAvatar();
    Boolean getGender();
    Boolean getStatus();
}
