package com.quick.sms.controller;

import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.PricingPlan;
import com.quick.sms.documents.TransactionRecharge;
import com.quick.sms.dto.request.price.BundlePriceRequest;
import com.quick.sms.dto.request.recharge.ApproveRechargeRequest;
import com.quick.sms.dto.request.recharge.RechargeRequest;
import com.quick.sms.service.PricingService;
import com.quick.sms.service.RechargeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recharge")
@CrossOrigin(origins="*")
@Slf4j
public class RechargeController {
    @Autowired
    RechargeService rechargeService;


    @PostMapping("")
    public Object makeRecharge(@Valid @RequestBody RechargeRequest rechargeRequest) throws Exception{
        return rechargeService.makeRecharge(rechargeRequest);
    }

    @PostMapping("/approve")
    public Object approveRecharge(@Valid @RequestBody ApproveRechargeRequest approveRechargeRequest) throws Exception{
        return rechargeService.approveRecharge(approveRechargeRequest);
    }

    @GetMapping("/{clientId}")
    public List<TransactionRecharge> getRechargeHistory(@PathVariable("clientId") String clientId) throws Exception{
        return rechargeService.getRechargeHistory(clientId);
    }






}
