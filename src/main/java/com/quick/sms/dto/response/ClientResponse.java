package com.quick.sms.dto.response;

import com.quick.sms.documents.Wallet;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClientResponse {
    private String id;
    private String userType;
    private String name;
    private String userName;
    private String phoneNumber;
    private Wallet wallet;
    private Date createDate;
    private Date updateDate;
    private String createdBy;
    private String status;

    public ClientResponse(String id, String userType, String name, String userName, String phoneNumber, Wallet wallet, Date createDate, Date updateDate, String status, String createdBy) {
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.wallet = wallet;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.createdBy =  createdBy;
    }
}
