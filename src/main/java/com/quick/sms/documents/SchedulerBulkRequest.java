package com.quick.sms.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class SchedulerBulkRequest {
    @Id
    private String id;
    private String bulkRequestId;
    private LocalDateTime scheduleTime;
    private String status;
}
