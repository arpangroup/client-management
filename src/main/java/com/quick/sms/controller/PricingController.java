package com.quick.sms.controller;

import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.PricingPlan;
import com.quick.sms.dto.request.price.BundlePriceRequest;
import com.quick.sms.service.PricingService;
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

    @PostMapping("/plan/create")
    public PricingPlan createPlan(@Valid @RequestBody PricingPlan pricingPlan) throws Exception{
        return pricingService.findOrCreate(pricingPlan);
    }

    @PostMapping("/bundle/create")
    public PricingBundle createBundle(@Valid @RequestBody BundlePriceRequest bundlePriceRequest) throws Exception{
        return pricingService.createBundlePrice(bundlePriceRequest);
    }

    @DeleteMapping("/plan/delete/{id}")
    public void deletePlan(@PathVariable("id") String id) throws Exception{
        pricingService.deletePlan(id);
    }

    @DeleteMapping("/bundle/delete{id}")
    public void deleteBundle(@PathVariable("id") String id) throws Exception{
        pricingService.deleteBundle(id);
    }


}
