package com.quick.sms.documents;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class BulkSmsRequest {

    @Id
    private String id;
    private String smsGateway;
    private String smsGatewayName;
    private String senderId;
    private Collection<String> recipientsList;
    private String messageType;
    private String message;
    private boolean isSchedulerRequest;
    private Boolean unSubscribeMessage;
    private Integer credits;
    private String updatedBy;//We use userid
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String status;

}
