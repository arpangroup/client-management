package com.quick.sms.repository;

import com.quick.sms.documents.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    public List<Client> findByUserType(String userType);
    public List<Client> findByCreatedBy(String creatorId);
    public Optional<Client> findByIdAndUserType(String id, String userType);
    public Optional<Client> findByUserNameAndPassword(String userName, String password);
    public Optional<Client> findByUserName(String userName);

    List<Client> findUserByPhoneNumberAndId(String phone, String clientId);
    public Optional<Client> findUserByPasswordAndId(String oldPassword, String userId);

}
