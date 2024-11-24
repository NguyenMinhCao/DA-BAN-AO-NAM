package vn.duantn.sominamshop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import vn.duantn.sominamshop.util.validator.RegisterChecked;
import vn.duantn.sominamshop.util.validator.StrongPassword;

@Getter
@Setter
@RegisterChecked
public class RegisterDTO {
    // các trường ứng với form đăng ký tài khoản

    @Size(min = 10, message = "Name tối thiểu 8 ký tự")
    private String fullName;

    private String email;

    @StrongPassword
    @NotBlank(message = "Không để trống password")
    private String password;

    @StrongPassword
    @Size(min = 3, message = "Confirm ít nhất 3 ký tự")
    private String confirmPassword;
}
