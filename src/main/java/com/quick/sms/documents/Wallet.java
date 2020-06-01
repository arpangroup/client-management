package com.quick.sms.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Wallet implements Serializable {
	private static final long serialVersionUID = -101947256442830320L;
	@Id
	private String id;
	private int totalCredit;
	private int usedCredit;
	private double openingBalance;
	private boolean active;

	public Wallet(int totalCredit, int usedCredit, double openingBalance, boolean active) {
		this.totalCredit = totalCredit;
		this.usedCredit = usedCredit;
		this.openingBalance = openingBalance;
		this.active = active;
	}
}
