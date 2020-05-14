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
    @NotNull(message = "{errors.user.userType.null}")
    @NotEmpty(message = "{errors.user.userType.empty}")
    private String userType;//UserType ==>[SUPER_ADMIN, ADMIN, RESELLER,USER]


    @Size(min = 3, max = 100, message = "{errors.user.name.size}")
    @NotNull(message = "{errors.user.name.null}")
    @NotEmpty(message = "{errors.user.name.empty}")
    private String name;

    private String email;

    @Size(min = 5, max = 200, message = "{errors.user.username.size}")
    @NotNull(message = "{errors.user.username.null}")
    @NotEmpty(message = "{errors.user.username.empty}")
    private String username;

    @Size(min = 5, max = 10, message = "{errors.user.password.size}")
    @NotNull(message = "{errors.user.password.null}")
    @NotEmpty(message = "{errors.user.password.empty}")
    private String password;

    //private String roles;


    @Size(min = 10, max = 10, message = "{errors.user.mobileNumber.size}")
    @NotNull(message = "{errors.user.mobileNumber.null}")
    @NotEmpty(message = "{errors.user.mobileNumber.empty}")
    private String phoneNumber;

    @NotNull(message = "{errors.user.routeId.null}")
    @NotEmpty(message = "{errors.user.routeId.empty}")
    private List<String> routeIdList; // ["PROMOTIONAL", "TRANSACTIONAL"]
    //private String assignRoute; // ["PROMOTIONAL", "TRANSACTIONAL"]

    private String gstno;
    private boolean isGstInclusive = false;
    //private String gstType;
    //private String gstPercentage;

    @NotNull(message = "{errors.user.routeId.null}")
    @NotEmpty(message = "{errors.user.routeId.empty}")
    private String creditType; //["PREPAID","POSTPAID"]
    @NotNull(message = "{errors.user.routeId.null}")
    @NotEmpty(message = "{errors.user.routeId.empty}")
    private String creditDeductionType ="SUBMIT";// ["SUBMIT", "DELIVERY"]
    private float creditLimit;

    private boolean applyDndReturn = false;
    private boolean applyDropping = false;
    private int droppingPercentage = 0;
    //private boolean adminDroppingApplicable = false;
    private boolean droppingAccessApplicableToChild = false;


    private boolean isBundlePriceApplicable = true;
    @NotNull(message = "{errors.user.routeId.null}")
    @NotEmpty(message = "{errors.user.routeId.empty}")
    private String pricingId;
    private float pricingAmount;
    private String bundlePriceId;

    private String creatorId;




}
