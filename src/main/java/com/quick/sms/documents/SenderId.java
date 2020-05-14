package com.quick.sms.documents;

import java.time.LocalDateTime;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class SenderId {
    @Id
    private String id;
    private String senderId;
    private String clientId;
    private String clientName;
    private String entityId;
    private String entityName;
    private String status;
    private Boolean isDeleted;
    private String createdBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String updatedBy;
    private String categoryId;
    private String categoryName;
    private String otp;
    private Binary documentFile;
    private String fileName;
    private String contentType;
    private String approvedBy;
    private Boolean isActive;
}
