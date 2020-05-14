package com.quick.sms.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class SmsTransaction {

    @Id
    private String id;
    private Integer amount;
    private String clientId;
    private LocalDateTime createdTime;
    private String type;
}
