package com.quick.sms.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document
@Data
@Builder
public class SMSLimit {
    @Id
    private String id;
    private String clientId;
    private int amount;
    private LocalDateTime updatedTime;
}
