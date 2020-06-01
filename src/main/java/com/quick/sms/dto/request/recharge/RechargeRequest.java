package com.quick.sms.dto.request.recharge;

import lombok.Data;

@Data
public class RechargeRequest {
    private String userId;
    private int noOfCredit;
    private double rechargeAmount;
    private String rechargeGateway;
    private String refNo;
}
