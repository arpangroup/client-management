package com.quick.sms.dto.request.profileupdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserDetails {
    private String clientId;
    private String dltRegNo;
    private String gstNo;
    private String country;
    private String state;
    private String address;

    private String companyType;
    private String companyName;
}
