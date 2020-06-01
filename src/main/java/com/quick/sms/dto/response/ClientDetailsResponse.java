package com.quick.sms.dto.response;

import com.quick.sms.documents.Address;
import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.PricingPlan;
import com.quick.sms.documents.Wallet;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class ClientDetailsResponse {
    private String id;
    private String userType;
    private String name;
    private String email;
    private String userName;
    private String password;
    private String phoneNumber;
    private List<RouteResponse> routes;
    private Address address;
    private String website;
    private String company;
    private String companyType;
    private String gstno;
    private boolean isGstInclusive;
    private String accountType;
    private String creditDeductionType;
    private float creditLimit;
    //DND_AND_DROPPING
    private boolean applyDndReturn;
    private boolean applyDropping;
    private int droppingPercentage;
    private boolean droppingAccessApplicableToChild;
    //PRICING
    private PricingResponse pricing;
    //Wallet
    private Wallet wallet;

    private Date createDate;
    private Date updateDate;
    private String status;

}
