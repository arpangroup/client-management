package com.quick.sms.dto.request.usercreation;

import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.PricingPlan;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class UserCreationDto {
    @NotNull(message = "userType cant be null")
    @NotEmpty(message = "userType cant be empty")
    private String userType;//UserType ==>[SUPER_ADMIN, ADMIN, RESELLER,USER]


    @Size(min = 3, max = 100, message = "name must be minimum 5 characters")
    @NotNull(message = "name cant be null")
    @NotEmpty(message = "name cant be empty")
    private String name;

    private String email;

    @Size(min = 5, max = 200, message = "Username must be minimum 5 characters")
    @NotNull(message = "username cant be null")
    @NotEmpty(message = "username cant be empty")
    private String username;

    @Size(min = 5, max = 10, message = "password cant be null")
    @NotNull(message = "password cant be null")
    @NotEmpty(message = "password cant be empty")
    private String password;

    //private String roles;


    @Size(min = 10, max = 15, message = "phoneNumber must be of 10 digits")
    @NotNull(message = "phoneNumber cant be null")
    @NotEmpty(message = "phoneNumber cant be empty")
    private String phoneNumber;

    @NotNull(message = "{errors.user.routeId.null}")
    @NotEmpty(message = "{errors.user.routeId.empty}")
    private List<String> routeIdList; // ["PROMOTIONAL", "TRANSACTIONAL"]
    //private String assignRoute; // ["PROMOTIONAL", "TRANSACTIONAL"]

    private String website;
    private String company;
    private String companyType;

    private String gstno;
    private boolean isGstInclusive = false;
    //private String gstType;
    //private String gstPercentage;

    @NotNull(message = "{errors.user.routeId.null}")
    @NotEmpty(message = "{errors.user.routeId.empty}")
    private String accountType; //["PREPAID","POSTPAID"]
    @NotNull(message = "{errors.user.routeId.null}")
    @NotEmpty(message = "{errors.user.routeId.empty}")
    private String creditDeductionType ="SUBMIT";// ["SUBMIT", "DELIVERY"]
    private float creditLimit;

    private boolean applyDndReturn = false;
    private boolean applyDropping = false;
    private int droppingPercentage = 0;
    //private boolean adminDroppingApplicable = false;
    private boolean droppingAccessApplicableToChild = false;


    private boolean bundlePriceApplicable;
    @NotNull(message = "pricingId cant be null")
    @NotEmpty(message = "pricingId cant be empty")
    private String pricingId;
    private int pricingAmount;
    private String bundlePriceId;

    private String creatorId;




}
