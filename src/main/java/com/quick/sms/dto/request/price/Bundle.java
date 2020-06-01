package com.quick.sms.dto.request.price;

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

    public Bundle(int startingUnit, int endingUnit, float unitPrice, float gstPercentage, boolean isGstInclusive) {
        this.startingUnit = startingUnit;
        this.endingUnit = endingUnit;
        this.unitPrice = unitPrice;
        this.gstPercentage = gstPercentage;
        this.isGstInclusive = isGstInclusive;
    }
}
