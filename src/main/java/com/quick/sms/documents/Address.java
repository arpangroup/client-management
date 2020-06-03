package com.quick.sms.documents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import lombok.experimental.Accessors;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Address implements Serializable {
	private static final long serialVersionUID = -1019476068442830320L;
	private String address;
	private String postalCode;
	private String city;
	private String state;
	private String country;
	private String phoneNumber;
}
