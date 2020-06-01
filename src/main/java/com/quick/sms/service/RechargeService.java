package com.quick.sms.service;

import com.quick.sms.documents.Invoice;
import com.quick.sms.documents.SmsTransaction;
import com.quick.sms.documents.SupportTicket;
import com.quick.sms.documents.TransactionRecharge;
import com.quick.sms.dto.authentication.ChangePasswordByOtpDto;
import com.quick.sms.dto.authentication.ChangePasswordDto;
import com.quick.sms.dto.authentication.ForgotPasswordDto;
import com.quick.sms.dto.authentication.ResetPasswordDto;
import com.quick.sms.dto.request.recharge.ApproveRechargeRequest;
import com.quick.sms.dto.request.recharge.RechargeRequest;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.dto.response.ClientDetailsResponse;
import com.quick.sms.dto.response.ClientResponse;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.LoginRequest;
import com.quick.sms.vo.Response;

import java.util.List;

public interface RechargeService {
    public TransactionRecharge makeRecharge(RechargeRequest rechargeRequest) throws Exception;
    public Boolean approveRecharge(ApproveRechargeRequest approveRechargeRequest) throws Exception;
    public List<TransactionRecharge> getRechargeHistory(String clientId) throws Exception;

}
