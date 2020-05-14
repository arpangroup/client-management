package com.quick.sms.repository;

import com.quick.sms.documents.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface OtpRepository extends MongoRepository<Otp, String> {
    Optional<Otp> findByOtp(String otp);
}
