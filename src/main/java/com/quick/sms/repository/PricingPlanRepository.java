package com.quick.sms.repository;

import com.quick.sms.documents.PricingPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PricingPlanRepository extends MongoRepository<PricingPlan, String> {
    Optional<PricingPlan> findByIdAndFixedPriceInPaisa(String id, int priceAmount);
    Optional<PricingPlan> findByIdAndCreatedUserId(String pricingId, String creatorId);
    List<PricingPlan> findAllByCreatedUserId(String userId);
}
