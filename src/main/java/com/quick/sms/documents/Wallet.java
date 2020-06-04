package com.quick.sms.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Wallet implements Serializable {
	private static final long serialVersionUID = -101947256442830320L;
	private double openingCredit;
	private double closingCredit;
	private double usedCredit;
	private boolean active;

	public Wallet(int openingCredit, int closingCredit, double usedCredit, boolean active) {
		this.openingCredit = openingCredit;
		this.closingCredit = closingCredit;
		this.usedCredit = usedCredit;
		this.active = active;
	}
}
