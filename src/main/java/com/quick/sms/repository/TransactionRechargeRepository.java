package com.quick.sms.repository;

import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.TransactionRecharge;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRechargeRepository extends MongoRepository<TransactionRecharge, String> {

    List<TransactionRecharge> findByClientId(String clientId);
}
