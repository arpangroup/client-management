package com.quick.sms.repository;

import com.quick.sms.documents.Pricing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PricingRepository extends MongoRepository<Pricing, String> {

}
