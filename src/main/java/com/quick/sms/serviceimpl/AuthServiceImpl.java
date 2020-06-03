package com.quick.sms.serviceimpl;

import com.quick.sms.documents.*;
import com.quick.sms.dto.ManageClient;
import com.quick.sms.dto.authentication.ChangePasswordByOtpDto;
import com.quick.sms.dto.authentication.ChangePasswordDto;
import com.quick.sms.dto.authentication.ForgotPasswordDto;
import com.quick.sms.dto.authentication.ResetPasswordDto;
import com.quick.sms.dto.request.profileupdate.UpdateUserDetails;
import com.quick.sms.dto.request.usercreation.SuperAdminCreation;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.dto.response.ClientDetailsResponse;
import com.quick.sms.dto.response.ClientResponse;
import com.quick.sms.dto.response.RouteResponse;
import com.quick.sms.enums.UserType;
import com.quick.sms.exception.ConflictException;
import com.quick.sms.exception.IdNotFoundException;
import com.quick.sms.exception.InvalidParameterException;
import com.quick.sms.repository.*;
import com.quick.sms.service.AuthService;
import com.quick.sms.service.ClientService;
import com.quick.sms.service.PricingService;
import com.quick.sms.utils.Constants;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.LoginRequest;
import com.quick.sms.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    OtpRepository otpRepository;

    public Client getClientByClientId(String userId) throws Exception {
        return clientRepository.findById(userId).orElseThrow(()->new IdNotFoundException("Invalid UserId"));
    }


    @Override
    public Response loginClient(LoginRequest loginRequest) throws Exception{
        System.out.println("==================LOGIN=========================");
        System.out.println(loginRequest);
        System.out.println("==================LOGIN=========================");
        Optional<Client> clientOptional = clientRepository.findByUserNameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if(clientOptional.isPresent()) {

            List<RouteResponse> routes = clientOptional.get().getAssignRoute().stream().map(Route::build).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login Success");
            response.put("id", clientOptional.get().getId());
            response.put("userType", clientOptional.get().getUserType());
            response.put("droppingAccessApplicableToChild", clientOptional.get().isDroppingAccessApplicableToChild());
            response.put("accountType", clientOptional.get().getAccountType());
            response.put("assignRoute", routes);
            response.put("wallet", clientOptional.get().getWallet());


            return new Response(true, 200, response, "jhgjgjgjgjgjgjjfhfhfhfhfhfhf");
        }
        return new Response(false, 400, "Login fail", null);
    }

    @Override
    public String forgotPassword(ForgotPasswordDto dto) throws Exception{
        if(dto.getPhone() != null){
            //Step1: Authenticate whether the provided mobile number or email is valid or not
            List<Client> clients = clientRepository.findUserByPhoneNumberAndId(dto.getPhone(), dto.getClientId());
            if(clients == null) throw new InvalidParameterException("Invalid ID or Mobile Number");
            Client client = clients.get(0);

            // Step2: Generate Random OTP and send using SMS Service
            Integer otpNumber = 10000 + new Random( System.currentTimeMillis() ).nextInt(20000); //generate 5-digit random OTP
            Otp otp = new Otp(dto.getClientId(), dto.getPhone(),otpNumber+"", 0);
            String message = "Dear user your OTP for resetting the password is"+otp;
            //boolean messageSent = sendMessage(authentication, dto.getPhone(), message);

            // Step3: Insert the OTP data into database
            //if(messageSent) otpRepository.save(otp);
            return "OTP has successfully send to register mobile number";

        }else{
            // Send OTP to email
            return "Resetting password by mail functionality is not available now";
        }
    }

    @Override
    public String changePasswordAfterLogin(ChangePasswordDto dto) throws Exception {
        // Step1: Check whether requested user exist or not using old-password and memberId
        List<Client> clients = clientRepository.findUserByPasswordAndId(dto.getOldPassword(), dto.getUserId());
        if(clients == null) throw new InvalidParameterException("Invalid Old Password");
        Client client = clients.get(0);

        // Step2: Update the modified record into database
        client.setPassword(dto.getNewPassword());
        clientRepository.save(client);
        return "Password changed successfully";
    }

    @Override
    public String changePasswordBeforeLogin(ChangePasswordByOtpDto dto) throws Exception {
        // Step1: Check whether requested user exist or not using old-password and memberId
        Optional<Otp> otpOptional = otpRepository.findByOtp(dto.getOtp());
        otpOptional.orElseThrow(()-> new IdNotFoundException("Invalid OTP"));
        Otp otp = otpOptional.get();
        Client client = clientRepository.findById(otp.getClientId()).get();

        // Step2: Update the modified record into database
        client.setPassword(dto.getNewPassword());
        clientRepository.save(client);

        // Step3: Invalidate the used OTP
        otp.setIsUsed(1);
        otp.setUpdateDate(new Date());
        otpRepository.save(otp);

        return "Password changed successfully";
    }

    @Override
    public String resetPassword(ResetPasswordDto dto) throws Exception {
        Optional<Client> clientOptional = clientRepository.findById(dto.getClientId());
        clientOptional.orElseThrow(()-> new IdNotFoundException("Invalid ClientID"));
        Client client = clientOptional.get();

        String newPassword = "password";
        if(dto.getResetPassword() != null) newPassword = dto.getResetPassword();
        client.setPassword(newPassword);
        clientRepository.save(client);
        return "Password reset successful";
    }

    @Override
    public Boolean blockUser(ManageClient manageClient) throws Exception {
        Client client = getClientByClientId(manageClient.getClientId());

        if(manageClient.isStatus()) client.setBlocked(true).setStatus("BLOCKED");
        else client.setBlocked(false).setStatus("UN-BLOCKED");
        clientRepository.save(client);

        return true;
    }

    @Override
    public Boolean deleteUser(ManageClient manageClient) throws Exception {
        Client client = getClientByClientId(manageClient.getClientId());

        if(manageClient.isStatus()) client.setDeleted(true).setStatus("DELETED");
        else client.setBlocked(false).setStatus("ACTIVE");
        clientRepository.save(client);
        return true;
    }

    @Override
    public Boolean activateUser(ManageClient manageClient) throws Exception {
        Client client = getClientByClientId(manageClient.getClientId());

        if(manageClient.isStatus()) client.setActive(true).setStatus("ACTIVE");
        else client.setBlocked(false).setStatus("INACTIVE");
        clientRepository.save(client);
        return true;
    }


}
