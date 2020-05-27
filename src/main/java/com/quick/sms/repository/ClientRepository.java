package com.quick.sms.repository;

import com.quick.sms.documents.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    public List<Client> findByUserType(String userType);
    public Optional<Client> findByIdAndUserType(String id, String userType);
    public Optional<Client> findByUserNameAndPassword(String userName, String password);
    public Optional<Client> findByUserName(String userName);

    List<Client> findUserByPhoneNumberAndId(String phone, String clientId);
    List<Client> findUserByPasswordAndId(String oldPassword, String userId);

}
