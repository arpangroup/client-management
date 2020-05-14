package com.quick.sms.service;


import com.quick.sms.documents.Invoice;
import com.quick.sms.vo.InputRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class InvoiceService {

    @Autowired
    MongoTemplate mongoTemplate;

    @SneakyThrows
    public void addNewInvoice(InputRequest inputRequest){

        Map<String, String> payload = inputRequest.getPayload();

        Invoice invoice = Invoice.builder()
                .clientId(payload.get("clientId"))
                .clientName(payload.get("clientName"))
                .invoiceType(payload.get("invoiceType"))
                .amount(Integer.parseInt(payload.get("amount")))
                .status("INITIAL")
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();

        mongoTemplate.save(invoice);
        log.info("Invoice created Successively.");
    }
}
