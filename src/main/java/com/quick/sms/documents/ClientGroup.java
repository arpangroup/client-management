package com.quick.sms.documents;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class ClientGroup {
    @Id
    private String id;
    private String groupName;
    private String status;
    private String createdBy;
    private Boolean isDeleted;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String updatedBy;

}
