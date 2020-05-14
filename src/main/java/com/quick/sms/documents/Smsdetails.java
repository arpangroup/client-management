package com.quick.sms.documents;


import lombok.Builder;
import lombok.Data;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@Builder
public class Smsdetails {

    @Id
    private String id;
    private String smsSenderId;
    private String smsMsisdn;
    private String smsMessage;
    private String status;
    private String bulkSmsRequestId;
    private LocalDateTime time;
    private String gatewayId;
    private Integer smsMessageType;

}
