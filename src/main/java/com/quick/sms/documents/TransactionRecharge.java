package com.quick.sms.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TransactionRecharge implements Serializable {
	private static final long serialVersionUID = -1019476068442830320L;
	@Id
	private String id;

	private String clientId;
	private int noOfCredits;
	private double rechargeAmount;
	private double openingBalance;
	private String rechargeGateway;
	private String refNo;

	@CreatedDate
	private Date createDate;
	@LastModifiedBy
	private String createdBy;
	@LastModifiedDate
	private Date updateDate;
	@LastModifiedBy
	private String updateBy;


	public TransactionRecharge(String clientId, int noOfCredits, double rechargeAmount, double openingBalance, String rechargeGateway,String refNo) {
		this.clientId = clientId;
		this.noOfCredits = noOfCredits;
		this.rechargeAmount = rechargeAmount;
		this.openingBalance = openingBalance;
		this.rechargeGateway = rechargeGateway;
		this.refNo = refNo;
	}
}
