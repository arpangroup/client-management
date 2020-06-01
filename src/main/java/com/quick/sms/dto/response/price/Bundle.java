package com.quick.sms.dto.response.price;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Bundle {
    private int startingUnit;
    private int endingUnit;
    private float unitPrice;
    private float gstPercentage;
    private boolean isGstInclusive = false;
    private String creatorUserId;

}
