package com.quick.sms.documents;


import lombok.Builder;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
public class DLTRegistration {

    @Id
    private String id;
    private String registrationId;
    private String entityType;
    private String orgCategory;
    private String pan;
    private Binary panDoc;
    private String cinOrGstOrTan;
    private Binary cinOrGstOrTanDoc;
    private String idProof;
    private Binary idProofDoc;
    private String otherDocName;
    private Binary otherDoc;
    private String authorisedName;
    private Binary authorisedDoc;
    private String designation;
    private Binary agreementDoc;
    private String createdBy;
    private LocalDateTime createdTime;
	private String panDocFileName;
	private String panDocContentType;
	private String cinOrGstOrTanDocFileName;
	private String cinOrGstOrTanDocContentType;
	private String idProofDocFileName;
	private String idProofDocContentType;
	private String otherDocFileName;
	private String otherDocContentType;
	private String authorisedDocFileName;
	private String authorisedDocContentType;
	private String agreementDocFileName;
	private String agreementDocContentType;
}
