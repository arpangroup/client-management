package com.quick.sms.dto.request.usercreation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
//@NoArgsConstructor
@Accessors(chain = true)
public class SuperAdminCreation {
    @Size(min = 5, max = 200, message = "{errors.user.username.size}")
    @NotNull(message = "{errors.user.username.null}")
    @NotEmpty(message = "{errors.user.username.empty}")
    private String username;

//    @NotNull(message = "{errors.user.userType.null}")
//    @NotEmpty(message = "{errors.user.userType.empty}")
    private String userType;//UserType ==>[SUPER_ADMIN, ADMIN, RESELLER,USER]

    @Size(min = 3, max = 100, message = "{errors.user.name.size}")
    @NotNull(message = "{errors.user.name.null}")
    @NotEmpty(message = "{errors.user.name.empty}")
    private String name;

    @Size(min = 5, max = 10, message = "{errors.user.password.size}")
    @NotNull(message = "{errors.user.password.null}")
    @NotEmpty(message = "{errors.user.password.empty}")
    private String password;

    public SuperAdminCreation(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.userType = "SUPER_ADMIN";
    }
}
