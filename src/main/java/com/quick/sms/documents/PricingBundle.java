package com.quick.sms.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "Bundle")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PricingBundle implements Serializable {
	private static final long serialVersionUID = -1019476068442830320L;
	@Id
	private String id;
	private int startingUnit;
	private int endingUnit;
	private float unitPrice;
	private float gstPercentage;
	private String creatorUserId;

	public PricingBundle(int startingUnit, int endingUnit, float unitPrice, float gstPercentage, String creatorUserId) {
		this.startingUnit = startingUnit;
		this.endingUnit = endingUnit;
		this.unitPrice = unitPrice;
		this.gstPercentage = gstPercentage;
		this.creatorUserId = creatorUserId;
	}
}
