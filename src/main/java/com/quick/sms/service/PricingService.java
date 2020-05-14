package com.quick.sms.service;

import com.quick.sms.documents.Pricing;
import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.PricingPlan;
import com.quick.sms.dto.authentication.ChangePasswordByOtpDto;
import com.quick.sms.dto.authentication.ChangePasswordDto;
import com.quick.sms.dto.authentication.ForgotPasswordDto;
import com.quick.sms.dto.authentication.ResetPasswordDto;

import java.util.List;

public interface PricingService {
    //Fixed Plan
   public PricingPlan findOrCreate(PricingPlan pricing) throws Exception;
   public PricingPlan findByPricingIdAndUserId(String pricingId, String userId) throws Exception;
   public PricingPlan findByPricingId(String pricingId) throws Exception;
   public List<PricingPlan> findAllPricing(String userId) throws Exception;
    // Bundle
    public PricingBundle findOrCreate(PricingBundle bundle) throws Exception;
    public PricingBundle findByBundleIdAndUserId(String bundleId, String userId) throws Exception;
    public PricingBundle findByBundleId(String bundleId) throws Exception;
    public List<PricingBundle> findAllBundle(String userId) throws Exception;

}
