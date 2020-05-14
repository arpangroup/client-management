package com.quick.sms.repository;

import com.quick.sms.documents.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends MongoRepository<Route, String> {
}
