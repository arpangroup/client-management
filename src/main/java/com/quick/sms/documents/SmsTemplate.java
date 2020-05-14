package com.quick.sms.documents;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class SmsTemplate {
    @Id
    private String id;
    private String templateName;
    private String message;
//    private String from;
//    private String status;
//    private String field;
//    private String template;
//    private Boolean isGlobal;
    private Boolean isDeleted;
    private String createdBy;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
