package com.quick.sms.controller;


import java.util.List;

import com.quick.sms.documents.*;
import com.quick.sms.dto.authentication.ChangePasswordByOtpDto;
import com.quick.sms.dto.authentication.ChangePasswordDto;
import com.quick.sms.dto.authentication.ForgotPasswordDto;
import com.quick.sms.dto.authentication.ResetPasswordDto;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.vo.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quick.sms.service.ClientService;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.utils.Utils;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.Response;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins="*")
@Slf4j
public class ClientMangementController {

    @Autowired
    ClientService clientService;

    @PostMapping("/addClient")
    public Response addClient(@Valid @RequestBody UserCreationDto dto){
        log.info("Adding client..., {}", dto);
        Response response = new Response();
        response.setResCode(200);
        try{
            clientService.createClient(dto);
            response.setResponse("Client Created Successfully.");
        }catch (Exception e){
            response.setResponse("There was something went wrong in server for handling the addClient request.");
            throw new SmsPortalGenException(e.getMessage());
        }
        return response;
    }

    @PostMapping("/updateClient")
    public Response updateClient(@Valid @RequestParam String request){
        log.info("updating client...");
        Response response = new Response();
        response.setResCode(200);
        InputRequest input = Utils.getObjectFromString(request);
        try{
            clientService.updateClient(input);
            response.setResponse("Client Updated Successfully.");
        }catch (Exception e){
            log.error("Error while updating client", e);
            response.setResponse("There was something went wrong in server for handling the updateClient request.");
            throw new SmsPortalGenException(e.getMessage());
        }
        return response;
    }

    @GetMapping("/getAllClients")
    public List<Client> getAllClients(){
       return clientService.getAllClients();
    }
    
    @GetMapping("/getClientDetails/{clientId}")
    public Client getAllClients(@PathVariable("clientId") String clientId) throws Exception{
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

    @PostMapping("/updateClientSmsLimit")
    public Response updateClientSmsLimit(@Valid @RequestBody InputRequest inputRequest){
        log.info("updating ClientSmsLimit...");
        Response response = new Response();
        response.setResCode(200);
        try {
            clientService.updateClientSmsLimit();
            response.setResponse("Updated SMS Balance successfully.");
        }catch (Exception e){
            log.error("Error while updating client sms limit", e);
            response.setResponse("There was something went wrong in server");
            throw new SmsPortalGenException(e.getMessage());
        }
        return response;
    }

    @GetMapping("/getClientSmsTransactions/{clientId}")
    public List<SmsTransaction> getClientSmsTransactions(@PathVariable("clientId") String clientId){
        return clientService.getClientSmsTransactions(clientId);
    }

    @GetMapping("/deleteClient/{clientId}")
    public Object deleteClient(@PathVariable("clientId") String clientId) throws Exception{
        log.info("Deleting client...");
        Response response = new Response();
        response.setResCode(200);
        try{
            clientService.deleteClient(clientId);
            response.setResponse("Client Deleted successfully.");
        }catch (Exception e){
        	response.setResponse("There was something went wrong in server.");
        	throw new Exception(e.getMessage());
        }
        return response;
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

    @GetMapping("client/updateStatus/{type}/{id}")
    public Response updateStatus(@PathVariable("id") String clientId, @PathVariable("type")  String status) {
        log.info("updating sender id...");
        Response response = new Response();
        response.setResCode(200);
        clientService.updateStatus(clientId, status);
        return response;
    }

    @GetMapping("client/get/{id}")
    public Response getClientById(@PathVariable("id") String clientId) throws Exception{
        log.info("updating sender id...");
        Response response = new Response();
        response.setResCode(200);
        Client client = clientService.getClientById(clientId);
        response.setResponse(null != client ? client : "Invalid details provided");
        return response;
    }

    @GetMapping("client/getBalance/{id}")
    public Response getSmsBalanceByClientId(@PathVariable("id") String clientId) {
        log.info("updating sender id...");
        Response response = new Response();
        response.setResCode(200);
        //Client client = clientService.getClientById(authentication, clientId);
        //response.setResponse(null != client ? client.getAssignedCredits() : "Invalid details provided");
        return response;
    }
    /*=====================================08-May-2020==============================================*/
    @PostMapping("/login")
    public Response login(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
        return clientService.loginClient(loginRequest);
    }

    @PostMapping("password/forgot")
    public String forgotPassword(@Valid @RequestBody Authentication authentication, ForgotPasswordDto forgotPasswordDto) throws Exception{
        return clientService.forgotPassword(forgotPasswordDto);
    }

    @PostMapping("password/change")
    public String changePasswordAfterLogin(@Valid @RequestBody ChangePasswordDto changePasswordDto) throws Exception{
        return clientService.changePasswordAfterLogin(changePasswordDto);
    }

    @PostMapping("password/change/by_otp")
    public String changePasswordBeforeLogin(@Valid @RequestBody ChangePasswordByOtpDto dto) throws Exception{
        return clientService.changePasswordBeforeLogin(dto);
    }

    @PostMapping("password/reset")
    public String resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) throws Exception{
        return clientService.resetPassword(resetPasswordDto);
    }
    /*==============================================================================================*/

}
