package com.quick.sms.dto.response.price;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BundlePriceResponse {
    private String planName;
    private List<Bundle> bundles;

    public BundlePriceResponse(String planName, List<Bundle> bundles) {
        this.planName = planName;
        this.bundles = bundles;
    }
}
