package com.quick.sms.dto.request.profileupdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserDetails {
    private String gstNo;
    private String website;
    private String company;
    private String address;
    private String state;

    private boolean applyDndReturn = false;
    private boolean applyDropping = false;
    private int droppingPercentage = 0;



}
