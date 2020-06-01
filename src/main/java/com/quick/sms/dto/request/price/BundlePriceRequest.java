package com.quick.sms.dto.request.price;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BundlePriceRequest {
    private String planName;
    private String creatorId;
    private List<Bundle> bundles;

    public BundlePriceRequest(String planName, List<Bundle> bundles, String creatorId) {
        this.planName = planName;
        this.bundles = bundles;
        this.creatorId = creatorId;
    }
}
