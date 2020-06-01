package com.quick.sms.serviceimpl;

import com.quick.sms.documents.*;
import com.quick.sms.dto.request.price.BundlePriceRequest;
import com.quick.sms.dto.response.price.Bundle;
import com.quick.sms.dto.response.price.BundlePriceResponse;
import com.quick.sms.exception.ConflictException;
import com.quick.sms.exception.IdNotFoundException;
import com.quick.sms.repository.*;
import com.quick.sms.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class PricingServiceImpl implements PricingService {

    @Autowired
    PricingPlanRepository pricingPlanRepository;

    @Autowired
    PricingBundleRepository pricingBundleRepository;

    @Override
    @Transactional
    public PricingPlan findOrCreate(PricingPlan pricing) throws Exception {
        if(pricing.getId() != null){
            Optional<PricingPlan> pricingPlan = pricingPlanRepository.findByIdAndPriceInPaisa(pricing.getId(), pricing.getPriceInPaisa());
            // If same price amount is exist then no need of creating new Pricing
            if(pricingPlan.isPresent()) return pricingPlan.get();
            PricingPlan pricingObj = new PricingPlan(pricing.getCreatedUserId(), pricing.getPriceInPaisa(), pricing.getPlanName(),pricing.getGstPercentage());
            return pricingPlanRepository.save(pricingObj);
        }else{
            PricingPlan pricingObj = new PricingPlan(pricing.getCreatedUserId(), pricing.getPriceInPaisa(), pricing.getPlanName(),pricing.getGstPercentage());
            return pricingPlanRepository.save(pricingObj);
        }
    }

    @Override
    public PricingPlan findByPricingIdAndUserId(String pricingId, String userId) throws Exception{
        return pricingPlanRepository.findByIdAndCreatedUserId(pricingId, userId).orElseThrow(()->new IdNotFoundException("Invalid PricingId"));
    }

    @Override
    public PricingPlan findByPricingId(String pricingId) throws Exception{
        return pricingPlanRepository.findById(pricingId).orElseThrow(()->new IdNotFoundException("Invalid PricingId"));
    }

    @Override
    public List<PricingPlan> findAllPricing(String userId) throws Exception{
        return pricingPlanRepository.findAllByCreatedUserId(userId);
    }

    @Override
    public void deletePlan(String id) throws Exception{
        pricingPlanRepository.deleteById(id);
    }

    @Override
    public PricingBundle createBundlePrice(BundlePriceRequest bundlePriceRequest) throws Exception {
        // Step1: Check same plan name already exist or not for the creator
        String planName = bundlePriceRequest.getPlanName().toLowerCase();
        String creatorId = bundlePriceRequest.getCreatorId();
        Optional<PricingBundle> pricingBundleOptional = pricingBundleRepository.findByPlanNameAndCreatorUserId(planName, creatorId);
        if (pricingBundleOptional.isPresent()) throw new ConflictException("Plan name already exist ");

        PricingBundle pricingBundle = new PricingBundle(planName, creatorId, bundlePriceRequest.getBundles());
        return pricingBundleRepository.save(pricingBundle);

    }

    @Override
    public PricingBundle findByBundleIdAndUserId(String bundleId, String userId) throws Exception{
        return pricingBundleRepository.findByIdAndCreatorUserId(bundleId, userId).orElseThrow(()->new IdNotFoundException("Invalid BundleID"));
    }

    @Override
    public PricingBundle findByBundleId(String bundleId) throws Exception{
        return pricingBundleRepository.findById(bundleId).orElseThrow(()->new IdNotFoundException("Invalid BundleID"));
    }

    @Override
    public List<PricingBundle> findAllBundle(String userId) throws Exception{
        return pricingBundleRepository.findAllByCreatorUserId(userId);
    }

    @Override
    public void deleteBundle(String id) throws Exception{
        pricingBundleRepository.deleteById(id);
    }
}
