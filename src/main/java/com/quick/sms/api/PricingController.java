package com.quick.sms.api;

import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.PricingPlan;
import com.quick.sms.service.InvoiceService;
import com.quick.sms.service.PricingService;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pricing")
@CrossOrigin(origins="*")
@Slf4j
public class PricingController {
    @Autowired
    PricingService pricingService;


    @GetMapping("/price/{clientId}/all")
    public List<PricingPlan> getAllPricingBasedOnClientId(@PathVariable("clientId") String clientId) throws Exception{
        return pricingService.findAllPricing(clientId);
    }


    @GetMapping("/bundle/{clientId}/all")
    public List<PricingBundle> getAllBundleBasedOnClientId(@PathVariable("clientId") String clientId) throws Exception{
        return pricingService.findAllBundle(clientId);
    }

    @GetMapping("/plan/create")
    public PricingPlan createPlan(@Valid @RequestBody PricingPlan pricingPlan) throws Exception{
        return pricingService.findOrCreate(pricingPlan);
    }

    @GetMapping("/bundle/create")
    public PricingBundle createBundle(@Valid @RequestBody PricingBundle pricingBundle) throws Exception{
        return pricingService.findOrCreate(pricingBundle);
    }
}
