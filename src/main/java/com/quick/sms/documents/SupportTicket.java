package com.quick.sms.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class SupportTicket {
    @Id
    private String id;
    private String clientId;
    private String subject;
    private String status;
    private String action;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
