package com.quick.sms.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    @NotNull(message = "Client ID can not be null")
    @NotEmpty(message = "Client ID can not be empty")
    private String clientId;

    //private String resetPassword;

}
