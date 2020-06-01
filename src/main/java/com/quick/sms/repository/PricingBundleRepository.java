package com.quick.sms.repository;

import com.quick.sms.documents.PricingBundle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PricingBundleRepository extends MongoRepository<PricingBundle, String> {
    Optional<PricingBundle> findByIdAndCreatorUserId(String pricingId, String creatorId);
    List<PricingBundle> findAllByCreatorUserId(String userId);

    Optional<PricingBundle> findByPlanNameAndCreatorUserId(String planName, String creatorUserId);
}
