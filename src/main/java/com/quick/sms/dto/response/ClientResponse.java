package com.quick.sms.dto.response;

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
    private int totalCredit;
    private int usedCredit;
    private Date createDate;
    private Date updateDate;
    private String status;

    public ClientResponse(String id, String userType, String name, String userName, String phoneNumber, int totalCredit, int usedCredit, Date createDate, Date updateDate, String status) {
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.totalCredit = totalCredit;
        this.usedCredit = usedCredit;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
    }
}
