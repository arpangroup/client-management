package com.quick.sms.documents;

import lombok.Builder;
import lombok.Data;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class Group {
    @Id
    private String id;
    private String groupName;
    private String status;
    private String createdBy;
    private Boolean isDeleted;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String updatedBy;
    
    @BsonIgnore
    private Integer contactsCount;

}
