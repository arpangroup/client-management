package com.quick.sms.controller;

import com.quick.sms.service.InvoiceService;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@CrossOrigin(origins="*")
@Slf4j
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/addNewInvoice")
    public Object addNewInvoice(@RequestBody InputRequest inputRequest){
        log.info("Adding new Invoice....");
        Response response = new Response();
        response.setResCode(200);
        if(inputRequest.getRequestType().equalsIgnoreCase("ADDNEWINVOICE")){
            try {
                invoiceService.addNewInvoice(inputRequest);
                response.setResponse("Invoice created successfully.");
            }catch (Exception e){
                response.setResponse("There was something went wrong in CreateNewInvoice service");
                throw new SmsPortalGenException(e.getMessage());
            }
        }else {
            response.setResponse("Not proper service");
        }
        return response;
    }
}
