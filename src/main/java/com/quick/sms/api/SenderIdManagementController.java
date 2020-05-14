package com.quick.sms.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.quick.sms.documents.SenderId;
import com.quick.sms.service.SenderIdService;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class SenderIdManagementController {

	public static final String UPDATESENDERID = "UPDATESENDERID";
	public static final String ADDSENDERID = "ADDSENDERID";
	@Autowired
	SenderIdService senderIdService;

	@PostMapping("/addSenderId")
	public Object addSenderId(Authentication authentication,
			@RequestPart(value = "documentFile", required = false) MultipartFile file,
			@RequestParam String request) {
		log.info("Adding sender id...");
		Response response = new Response();
		response.setResCode(200);
		InputRequest inputRequest;
		try {
			inputRequest = new Gson().fromJson(request, InputRequest.class);
		} catch (Exception e1) {
			throw new SmsPortalGenException("Invalid input provided");
		}
		if (inputRequest.getRequestType().equalsIgnoreCase(ADDSENDERID)) {
			try {
				senderIdService.saveSenderId(authentication, inputRequest, file);
				response.setResponse("Sender Id added successfully");
			} catch (Exception e) {
				response.setResponse("There was something went wrong in server");
				throw new SmsPortalGenException(e.getMessage());
			}
		} else {
			response.setResponse("Not a valid service");
		}
		return response;
	}

	@PostMapping("/updateSenderId")
	public Object updateSenderId(Authentication authentication,
			@RequestPart(value = "documentFile", required = false) MultipartFile file, 
			@RequestParam String request) {
		log.info("updating sender id...");
		Response response = new Response();
		response.setResCode(200);
		InputRequest inputRequest;
		try {
			inputRequest = new Gson().fromJson(request, InputRequest.class);
		} catch (Exception e1) {
			throw new SmsPortalGenException("Invalid input provided");
		}
		if (inputRequest.getRequestType().equalsIgnoreCase(UPDATESENDERID)) {
			try {
				senderIdService.updateSenderId(authentication, inputRequest, file);
				response.setResponse("Sender Id updated successfully");
			} catch (Exception e) {
				response.setResponse("There was something went wrong in server");
				throw new SmsPortalGenException(e.getMessage());
			}
		} else {
			response.setResponse("Not a valid service");
		}
		return response;
	}

	@GetMapping("getAllSenderIds")
	public List<SenderId> getAllSenderIds(Authentication authentication) {
		return senderIdService.getAllSenderIds(authentication);
	}
	
	@GetMapping("getActiveSenderIds")
	public List<SenderId> getActiveSenderIds(Authentication authentication) {
		return senderIdService.getActiveSenderIds(authentication);
	}
	
	@GetMapping("updateStatus/{type}/{id}")
	public Response updateStatus(Authentication authentication,@PathVariable("id") String senderId, @PathVariable("type")  String status) {
		log.info("updating sender id...");
		Response response = new Response();
		response.setResCode(200);
		senderIdService.approveSenderId(authentication, senderId, status);
		return response;
	}

	@GetMapping("deleteSenderId")
	public Object deleteSmsTemplate(Authentication authentication, @RequestParam String senderId) {
		log.info("Deleting Sender id...");
		Response response = new Response();
		response.setResCode(200);
		try {
			senderIdService.deleteSenderId(authentication, senderId);
			response.setResponse("Sender ID Deleted successfully.");
		} catch (Exception e) {
			log.info("Error in delete SenderId Details: {}", e);
			response.setResponse("There was something went wrong in server");
			throw new SmsPortalGenException(e.getMessage());
		}

		return response;
	}

	@PostMapping("/downloadDocument/{id}")
	public void retrieveFile(@PathVariable("id") String id, HttpServletResponse response) {
		log.info("Downloading document...");
		SenderId senderid = senderIdService.getSenderId(id);
		byte[] byteArray = new byte[1];
		if(null != senderid) {
			byteArray = senderid.getDocumentFile().getData();
			response.setContentType(senderid.getContentType());
			response.setHeader("Content-Disposition", "filename=" + senderid.getFileName());
			response.setContentLength(byteArray.length);
		} 
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			os.write(byteArray, 0, byteArray.length);
		} catch (Exception excp) {
			// handle error
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
