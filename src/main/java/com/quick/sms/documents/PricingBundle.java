package com.quick.sms.documents;

import com.quick.sms.dto.request.price.Bundle;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Bundle")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PricingBundle implements Serializable {
	private static final long serialVersionUID = -1019476068442830320L;
	@Id
	private String id;
	private String planName;
	private String creatorUserId;

	private List<Bundle> bundles = new ArrayList<>();

	public PricingBundle(String planName, String creatorUserId, List<Bundle> bundles) {
		this.planName = planName;
		this.creatorUserId = creatorUserId;
		this.bundles = bundles;

//		this.startingUnit = startingUnit;
//		this.endingUnit = endingUnit;
//		this.unitPrice = unitPrice;
//		this.gstPercentage = gstPercentage;
//		this.creatorUserId = creatorUserId;
	}
}
