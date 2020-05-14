package com.quick.sms.documents;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the gateway_credentials database table.
 * 
 */
@Getter
@Setter
@Document
@Builder
public class GatewayCredential implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String credentialsId;

	private String apiPassword;

	private String apiUsername;

	private String gatewayId;
	
	public String createdBy;

	public LocalDateTime createdOn;

	public String updatedBy;

	private LocalDateTime updatedOn;

}