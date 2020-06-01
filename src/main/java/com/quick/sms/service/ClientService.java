package com.quick.sms.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.quick.sms.dto.ManageClient;
import com.quick.sms.dto.authentication.ChangePasswordByOtpDto;
import com.quick.sms.dto.authentication.ChangePasswordDto;
import com.quick.sms.dto.authentication.ForgotPasswordDto;
import com.quick.sms.dto.authentication.ResetPasswordDto;
import com.quick.sms.dto.request.profileupdate.UpdateUserDetails;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.dto.response.ClientDetailsResponse;
import com.quick.sms.dto.response.ClientResponse;
import com.quick.sms.vo.LoginRequest;
import com.quick.sms.vo.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.quick.sms.documents.Client;
import com.quick.sms.documents.DLTRegistration;
import com.quick.sms.documents.Invoice;
import com.quick.sms.documents.Route;
import com.quick.sms.documents.SmsTransaction;
import com.quick.sms.documents.SupportTicket;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.vo.InputRequest;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

public interface ClientService {
    public ClientDetailsResponse createClient(UserCreationDto userCreationDto) throws Exception;
    public ClientDetailsResponse updateClient(UpdateUserDetails updateUserDetails) throws Exception;
    public Response loginClient(LoginRequest loginRequest) throws Exception;


    public List<ClientResponse> getAllClients();
    public List<ClientResponse> getAllClientsByUserType(String userType);
    public List<ClientResponse> getAllClientsUnderParentId(String parentId)throws Exception;
    public ClientDetailsResponse getClientByIdAndType(String userId, String userType) throws Exception;
    public ClientDetailsResponse getClientById(String userId) throws Exception;

    public List<SupportTicket> getClientSupportTickets(String clientId);
    public List<Invoice> getClientInvoices(String clientId);
    public List<SmsTransaction> getClientSmsTransactions(String clientId);
    public void deleteClient(String clientId);

    public void updateClientSmsLimit();
    public void updateStatus(String clientId, String status);


    public String forgotPassword(ForgotPasswordDto dto) throws Exception;
    public String changePasswordAfterLogin(ChangePasswordDto dto) throws Exception;
    public String changePasswordBeforeLogin(ChangePasswordByOtpDto dto) throws Exception;
    public String resetPassword(ResetPasswordDto dto) throws Exception;


    public Boolean blockUser(ManageClient manageClient) throws Exception;
    public Boolean deleteUser(ManageClient manageClient) throws Exception;
    public Boolean activateUser(ManageClient manageClient) throws Exception;
}
