package com.quick.sms.service;

import com.quick.sms.documents.Invoice;
import com.quick.sms.documents.SmsTransaction;
import com.quick.sms.documents.SupportTicket;
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

import java.util.List;

public interface AuthService {
    public Response loginClient(LoginRequest loginRequest) throws Exception;

    public String forgotPassword(ForgotPasswordDto dto) throws Exception;
    public String changePasswordAfterLogin(ChangePasswordDto dto) throws Exception;
    public String changePasswordBeforeLogin(ChangePasswordByOtpDto dto) throws Exception;
    public String resetPassword(ResetPasswordDto dto) throws Exception;

    public Boolean blockUser(ManageClient manageClient) throws Exception;
    public Boolean deleteUser(ManageClient manageClient) throws Exception;
    public Boolean activateUser(ManageClient manageClient) throws Exception;
}
