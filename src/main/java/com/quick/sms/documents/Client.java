package com.quick.sms.documents;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.dto.response.ClientDetailsResponse;
import com.quick.sms.dto.response.RouteResponse;
import lombok.*;
import lombok.experimental.Accessors;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
@Accessors(chain = true)
public class Client implements Serializable {
	private static final long serialVersionUID = -1019476068442830320L;
	@Id
	private String id;
	//BASIC_INFO:
	private String userType;
	private String name;
	private String email;
	private String userName;
	private String password;
	private String phoneNumber;
	private List<Route> assignRoute;
	//ADDRESS:
	private Address address;
	private String website;
	private String company;
	private String companyType;//["Pvt Ltd. Company", ""]
	//GST:
	private String gstno;
	private boolean gstInclusive = false; //<=======()()############()()===============================[ARPAN]
	//CREDIT_CONTROL:
	private String accountType; // prepaid / postpaid
	private String creditDeductionType ="SUBMIT";// ["SUBMIT", "DELIVERED"] <===> private String billingType; // Submit / Delivery
	private float creditLimit=0.0f; // If user select postpaid
	//DND_AND_DROPPING
	private boolean applyDndReturn = false;//<=======()()############()()===============================[ARPAN]
	private boolean applyDropping = false;//<=======()()############()()===============================[ARPAN]
	private int droppingPercentage = 0;//<=======()()############()()===============================[ARPAN]
	private boolean droppingAccessApplicableToChild = false;//<=======()()############()()===============================[ARPAN]
	//PRICING
	private boolean bundlePriceApplicable = true; //otherwise BundlePrice will apply<=======()()############()()===============================[ARPAN]
	private PricingPlan pricing;
	private PricingBundle bundle;
	//wallet
	private Wallet wallet;
	//DLT_REGISTRATION_DATA
//	@BsonIgnore
//	private DLTRegistration dltRegistration;

	//private Integer assignedCredits;
	//private String notifyWithEmail;
	//private String smsType;
	//private String smsTemplateType;
	//private String routeName;
	//private String apiKey;
	//private String price;

	@CreatedDate
	private Date createDate;
	private String createdBy;
	@LastModifiedDate
	private Date updateDate;
	private String updatedBy;

	private Boolean deleted = false;
	private Boolean blocked = false;
	private Boolean active = false;
	private String status = "ACTIVE";
	//private Binary avatar;
	//private String avatarFileName;
	//private String avatarFileType;


	public Client build(UserCreationDto requestObj, String userType, List<Route> route, PricingPlan pricingPlan, PricingBundle pricingBundle, String creatorId){
		float creditLimit = 0.0f;
		if(requestObj.getAccountType().toUpperCase().equals("POSTPAID"))
			creditLimit = requestObj.getCreditLimit();

		Wallet wallet = new Wallet(10, 0, 0.0, true);

		Client client = new Client()
				.setUserType(userType)
				.setName(requestObj.getName())
				.setEmail(requestObj.getEmail())
				.setUserName(requestObj.getUsername())
				.setPassword(requestObj.getPassword())
				.setPhoneNumber(requestObj.getPhoneNumber())
				.setAssignRoute(route)
				//.setAddress(requestObj.getA)
				//.setWebsite(requestObj.getWebsite())
				.setCompany(requestObj.getCompany())
				.setCompanyType(requestObj.getCompanyType())
				.setGstno(requestObj.getGstno())
				//.setGstType(requestObj.getGstType())
				//.setGstPercentage(requestObj.getGstPercentage())
				.setGstInclusive(requestObj.isGstInclusive())
				//.setRoles(requestObj.getRoles())
				//Prepaid User
				.setAccountType(requestObj.getAccountType())
				.setCreditDeductionType(requestObj.getCreditDeductionType())
				.setCreditLimit(creditLimit)
				.setApplyDndReturn(requestObj.isApplyDndReturn())
				.setApplyDropping(requestObj.isApplyDropping())
				.setDroppingPercentage(requestObj.getDroppingPercentage())
				.setDroppingAccessApplicableToChild(requestObj.isDroppingAccessApplicableToChild())
				.setBundlePriceApplicable(requestObj.isBundlePriceApplicable())
				.setPricing(pricingPlan)
				.setBundle(pricingBundle)
				.setWallet(wallet)
				.setCreatedBy(creatorId)
				//Only for Postpaid user
				.setCreditLimit(requestObj.getCreditLimit());

		return client;
	}

	public static ClientDetailsResponse build(Client obj){
		List<RouteResponse> routeResponses = obj.getAssignRoute().stream().map(Route::build).collect(Collectors.toList());

		return new ClientDetailsResponse()
				.setId(obj.getId())
				.setUserType(obj.getUserType())
				.setName(obj.getName())
				.setEmail(obj.getEmail())
				.setUserName(obj.getUserName())
				.setPassword(obj.getPassword())
				.setPhoneNumber(obj.getPhoneNumber())
				.setRoutes(routeResponses)
				.setAddress(obj.getAddress())
				.setWebsite(obj.getWebsite())
				.setCompany(obj.getCompany())
				.setCompanyType(obj.getCompanyType())
				.setGstno(obj.getGstno())
				.setGstInclusive(obj.isGstInclusive())
				//Prepaid User
				.setAccountType(obj.getAccountType())
				.setCreditDeductionType(obj.getCreditDeductionType())
				.setCreditLimit(obj.getCreditLimit())
				.setApplyDndReturn(obj.isApplyDndReturn())
				.setApplyDropping(obj.isApplyDropping())
				.setDroppingPercentage(obj.getDroppingPercentage())
				.setDroppingAccessApplicableToChild(obj.isDroppingAccessApplicableToChild())
				//.setPricing(pricingPlan)
				.setWallet(obj.getWallet())
				.setCreateDate(obj.getCreateDate())
				.setUpdateDate(obj.getUpdateDate())
				.setStatus(obj.getStatus());
	}
}
