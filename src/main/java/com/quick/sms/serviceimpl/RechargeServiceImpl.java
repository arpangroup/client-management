package com.quick.sms.serviceimpl;

import com.quick.sms.documents.*;
import com.quick.sms.dto.request.price.BundlePriceRequest;
import com.quick.sms.dto.request.recharge.ApproveRechargeRequest;
import com.quick.sms.dto.request.recharge.RechargeRequest;
import com.quick.sms.exception.ConflictException;
import com.quick.sms.exception.IdNotFoundException;
import com.quick.sms.repository.ClientRepository;
import com.quick.sms.repository.PricingBundleRepository;
import com.quick.sms.repository.PricingPlanRepository;
import com.quick.sms.repository.TransactionRechargeRepository;
import com.quick.sms.service.PricingService;
import com.quick.sms.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TransactionRechargeRepository transactionRechargeRepository;

    @Override
    public TransactionRecharge makeRecharge(RechargeRequest rechargeRequest) throws Exception {
        Optional<Client> clientOptional =  clientRepository.findById(rechargeRequest.getUserId());
        clientOptional.orElseThrow(()-> new IdNotFoundException("Invalid ClientId"));
        Client client = clientOptional.get();

         double rechargeAmount = rechargeRequest.getRechargeAmount();
         double openingBalance = client.getWallet().getOpeningBalance() + rechargeAmount;
         int totalCredits = client.getWallet().getTotalCredit() + rechargeRequest.getNoOfCredit();

        // Make Log object
        TransactionRecharge transactionRecharge = new TransactionRecharge(client.getId(), rechargeRequest.getNoOfCredit(), rechargeRequest.getRechargeAmount(), openingBalance, rechargeRequest.getRechargeGateway(), rechargeRequest.getRefNo());

        // Update wallet of the client:
        client.getWallet().setOpeningBalance(openingBalance).setTotalCredit(totalCredits);

        clientRepository.save(client);
        TransactionRecharge rechageResp = transactionRechargeRepository.save(transactionRecharge);

        return rechageResp;

    }

    @Override
    public Boolean approveRecharge(ApproveRechargeRequest request) throws Exception {
        Optional<Client> clientOptional =  clientRepository.findById(request.getClientId());
        clientOptional.orElseThrow(()-> new IdNotFoundException("Invalid ClientId"));
        Client client = clientOptional.get();

        client.getWallet().setActive(true);
        return true;
    }

    @Override
    public List<TransactionRecharge> getRechargeHistory(String clientId) throws Exception {
        return transactionRechargeRepository.findByClientId(clientId);
    }
}
