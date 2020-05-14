package com.quick.sms.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordByOtpDto {
    @NotNull(message = "otp can not be null")
    @NotEmpty(message = "Otp can not be empty")
    private String otp;

    @NotNull(message = "Password can not be null")
    @NotEmpty(message = "Password can not be empty")
    private String newPassword;
}
