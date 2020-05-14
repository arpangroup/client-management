/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quick.sms.documents;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author suresh
 */
@Document
@Data
@Builder
public class Contacts{

	@Id
    private String id;
    private String contactName;
    private String mobile;
    private String email;
    private String groupName;
    private String groupId;
    private String createdBy;
    private LocalDateTime createdOn;

}
