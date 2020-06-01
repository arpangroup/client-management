package com.quick.sms.dto;

import lombok.Data;

@Data
public class ManageClient {
    private String creatorId;
    private String clientId;
    private String description;
    private boolean status = true;
}
