package com.quick.sms.dto.request.recharge;

import lombok.Data;

@Data
public class ApproveRechargeRequest {
    private String approverId;
    private String clientId;
}
