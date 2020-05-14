package com.quick.sms.documents;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "otp")
@Data
@Accessors(chain = true)
public class Otp {
    @Id
    private Long id;

    private String clientId;
    private String phone;
    private String otp;
    private int isUsed = 0;

    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date updateDate;

    public Otp() {
    }

    public Otp(String clientId, String phone, String otp, int isUsed) {
        this.clientId = clientId;
        this.phone = phone;
        this.otp = otp;
        this.isUsed = isUsed;
    }


}
