package com.quick.sms.controller;


import java.util.List;

import com.quick.sms.documents.*;
import com.quick.sms.dto.authentication.ChangePasswordByOtpDto;
import com.quick.sms.dto.authentication.ChangePasswordDto;
import com.quick.sms.dto.authentication.ForgotPasswordDto;
import com.quick.sms.dto.authentication.ResetPasswordDto;
import com.quick.sms.dto.request.profileupdate.UpdateUserDetails;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.dto.response.ClientDetailsResponse;
import com.quick.sms.dto.response.ClientResponse;
import com.quick.sms.vo.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.quick.sms.service.ClientService;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.utils.Utils;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.Response;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins="*")
@Slf4j
public class ClientMangementController {

    @Autowired
    ClientService clientService;

    @PostMapping("/addClient")
    public ClientDetailsResponse addClient(@Valid @RequestBody UserCreationDto dto) throws Exception{
        log.info("Adding client..., {}", dto);
        return clientService.createClient(dto);
    }

    @PutMapping("/updateClient")
    public Response updateClient(@Valid @RequestBody UpdateUserDetails updateUserDetails){
        log.info("updating client...");
        Response response = new Response();
        response.setResCode(200);
        try{
            clientService.updateClient(updateUserDetails);
            response.setResponse("Client Updated Successfully.");
        }catch (Exception e){
            log.error("Error while updating client", e);
            response.setResponse("There was something went wrong in server for handling the updateClient request.");
            throw new SmsPortalGenException(e.getMessage());
        }
        return response;
    }

    @GetMapping("/getAllClients")
    public List<ClientResponse> getAllClients(){
       return clientService.getAllClients();
    }

    @GetMapping("/getAllClients/{parentId}")
    public List<ClientResponse> getAllClientsUnderParentId(@PathVariable("parentId") String parentId) throws Exception{
        return clientService.getAllClientsUnderParentId(parentId);
    }



    @GetMapping("/getClientDetails/{clientId}")
    public ClientDetailsResponse getClientById(@PathVariable("clientId") String clientId) throws Exception{
       return clientService.getClientById(clientId);
    }

    @GetMapping("/getClientSupportTickets/{clientId}")
    public List<SupportTicket> getClientSupportTickets(@PathVariable("clientId") String clientId){
        return clientService.getClientSupportTickets(clientId);
    }

    @GetMapping("/getClientInvoices/{clientId}")
    public List<Invoice> getClientInvoices(@PathVariable("clientId") String clientId){
        return clientService.getClientInvoices(clientId);
    }


    @GetMapping("/getClientSmsTransactions/{clientId}")
    public List<SmsTransaction> getClientSmsTransactions(@PathVariable("clientId") String clientId){
        return clientService.getClientSmsTransactions(clientId);
    }

    
//    @GetMapping("client/generateApiKey")
//    public Object generateApiKey(Authentication authentication) {
//        log.info("Deleting client...");
//        Response response = new Response();
//        response.setResCode(200);
//        try{
//           //String key = clientService.generateApiKey(authentication);
//            String key = null;
//           response.setResponse(key);
//        }catch (Exception e){
//        	response.setResponse("There was something went wrong in server.");
//        	throw new SmsPortalGenException(e.getMessage());
//        }
//        return response;
//    }


}
