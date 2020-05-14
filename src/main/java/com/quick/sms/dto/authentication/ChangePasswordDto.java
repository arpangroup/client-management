package com.quick.sms.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    @NotNull(message = "Client ID can not be null")
    @NotEmpty(message = "Client ID can not be empty")
    private String userId;

    @NotNull(message = "Client ID can not be null")
    @NotEmpty(message = "Client ID can not be empty")
    private String oldPassword;

    @NotNull(message = "Client ID can not be null")
    @NotEmpty(message = "Client ID can not be empty")
    private String newPassword;
}
