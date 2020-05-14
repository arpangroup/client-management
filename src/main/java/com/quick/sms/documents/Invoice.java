package com.quick.sms.documents;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class Invoice {
    @Id
    private String id;
    private String clientId;
    private String clientName;
    private String invoiceType;
    private String status;
    private int amount;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
