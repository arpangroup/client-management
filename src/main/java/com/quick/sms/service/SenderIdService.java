package com.quick.sms.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.quick.sms.documents.SenderId;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.vo.InputRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SenderIdService {

    @Autowired
    MongoTemplate mongoTemplate;

    public void saveSenderId(Authentication authentication, InputRequest inputRequest, MultipartFile file){
        /*
    	UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        Map<String,String> payload = inputRequest.getPayload();
        List<String> sendersList = Arrays.asList(payload.get("senderIds").split(","));

        List<SenderId> senders = new ArrayList<>();
        sendersList.forEach(sender->{
            SenderId senderId = SenderId.builder()
                    .clientId(payload.get("clientId"))
                    .clientName(payload.get("clientName"))
                    .senderId(sender)
                    .status("Pendind Approval")
                    .isDeleted(false)
                    .createdBy(user.getUsername())
                    .createdTime(LocalDateTime.now())
                    .updatedTime(LocalDateTime.now())
                    .entityId(payload.get("entityId"))
                    .entityName(payload.get("entityName"))
                    .categoryId(payload.get("categoryId"))
                    .categoryName(payload.get("categoryName"))
                    .otp(payload.get("otp"))
                    .isActive(StringUtils.isNoneBlank(payload.get("isActive")) ? Boolean.valueOf(payload.get("isActive")) : false)
                    .build();
            if(null!= file) {
            	try {
					senderId.setDocumentFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
					senderId.setFileName(file.getOriginalFilename());
					senderId.setContentType(file.getContentType());
				} catch (IOException e) {
					log.error("Error while saving file", e);
				}
            }
            senders.add(senderId);
        });

        mongoTemplate.insertAll(senders);
        log.info("SenderIds are saved successfully.");

         */
    }

    public void updateSenderId(Authentication authentication, InputRequest inputRequest, MultipartFile file){
        /*
    	UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        Map<String,String> payload = inputRequest.getPayload();
        SenderId oldSenderId = mongoTemplate.findById(payload.get("id"),SenderId.class);
        if(oldSenderId == null) {
        	throw new SmsPortalGenException("Sender id not found with the data provided to edit.");
        }
        SenderId senderId = SenderId.builder()
                .id(payload.get("id"))
                .clientId(StringUtils.isNotBlank(payload.get("clientId")) ? payload.get("clientId") : oldSenderId.getClientId())
                .clientName(StringUtils.isNoneBlank(payload.get("clientName")) ? payload.get("clientName") : oldSenderId.getClientName()) 
                .senderId(StringUtils.isNoneBlank(payload.get("senderId")) ? payload.get("senderId") : oldSenderId.getSenderId())
                .status("Pending Approval")
                .createdBy(oldSenderId.getCreatedBy())
                .isDeleted(false)
                .updatedBy(user.getUsername())
                .updatedTime(LocalDateTime.now())
                .entityId(StringUtils.isNoneBlank(payload.get("entityId")) ? payload.get("entityId") : oldSenderId.getEntityId())
                .entityName(StringUtils.isNoneBlank(payload.get("entityName")) ? payload.get("entityName") : oldSenderId.getEntityName())
                .categoryId(StringUtils.isNoneBlank(payload.get("categoryId")) ? payload.get("categoryId") : oldSenderId.getCategoryId())
                .categoryName(StringUtils.isNoneBlank(payload.get("categoryName")) ? payload.get("categoryName") : oldSenderId.getCategoryName())
                .otp(StringUtils.isNoneBlank(payload.get("otp")) ? payload.get("otp") : oldSenderId.getOtp())
                .isActive(StringUtils.isNoneBlank(payload.get("isActive")) ? Boolean.valueOf(payload.get("isActive")) : false)
                .build();
        if(null!= file) {
        	try {
				senderId.setDocumentFile(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
				senderId.setFileName(file.getOriginalFilename());
				senderId.setContentType(file.getContentType());
			} catch (IOException e) {
				log.error("Error while saving file", e);
			}
        }
        mongoTemplate.save(senderId);

         */
    }

    public List<SenderId> getAllSenderIds(Authentication authentication){
        /*
    	UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        Query query = new Query();
        Criteria criteria = Criteria.where("isDeleted").is(false);
        if(!"superadmin".equals(user.getUserType())){
        	criteria.and("createdBy").is(user.getUsername());
        }
        query.addCriteria(criteria);
        return mongoTemplate.find(query, SenderId.class);

         */
        return new ArrayList<>();
    }
    
    public SenderId getSenderId(String id){
        return mongoTemplate.findById(id, SenderId.class);
    }

    public void deleteSenderId(Authentication authentication, String senderId){
        SenderId sender = mongoTemplate.findById(senderId,SenderId.class);
        sender.setIsDeleted(true);
        mongoTemplate.save(sender);
    }
    
    public void approveSenderId(Authentication authentication, String senderId, String status){
        /*
    	UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        SenderId sender = mongoTemplate.findById(senderId,SenderId.class);
        sender.setStatus(status);
        sender.setApprovedBy(user.getUsername());
        mongoTemplate.save(sender);

         */
    }

	public List<SenderId> getActiveSenderIds(Authentication authentication) {
        /*
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Query query = new Query();
		query.addCriteria(Criteria.where("isDeleted").is(false).and("createdBy").is(user.getUsername()).and("status")
				.is("Approved"));
		return mongoTemplate.find(query, SenderId.class);

         */
        return new ArrayList<>();
	}
}
