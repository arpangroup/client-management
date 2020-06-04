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

        Wallet wallet = new Wallet(10, 10, 0.0, true);// closing = openung-used

        /*
        Wallet(10, 9, 1.0, true);// closing = opening-used
        private String userId;
        private int noOfCredit;
        private double rechargeAmount;
        private String rechargeGateway;
        private String refNo;



            Previously:
                openingCredit : 10;       UsedCredit: 1;       closingCredit: (opening-used) = 9
                =====================================================================================
100 Recharge:   (=openingBal) => 9            0                     (closingBal + newRecharge) =9 +100 =109

10 Used                9                      10                           99
1 used                 9                      11                           98
5 used                 9                      16                           93
500 Recharge:  (=LastClosingBal) => 93         0                    (NewOpeningBal + newRecharge) =93 +500 =


         */


        double lastOpeningStock = client.getWallet().getOpeningCredit();
        double usedStock        = client.getWallet().getUsedCredit();
        double lastClosingStock = client.getWallet().getClosingCredit();

        // After Recharge:
        double newCurrentStock     = rechargeRequest.getRechargeAmount();  //100
        double currentOpeningStock = lastOpeningStock;
        usedStock                  = 0.0f;
        double currentClosingStock = currentOpeningStock + newCurrentStock;




        // Make Log object
        TransactionRecharge transactionRecharge = new TransactionRecharge(client.getId(), rechargeRequest.getNoOfCredit(), rechargeRequest.getRechargeAmount(), currentOpeningStock, currentClosingStock, rechargeRequest.getRechargeGateway(), rechargeRequest.getRefNo());

        // Update wallet of the client:
        client.getWallet().setOpeningCredit(currentOpeningStock).setUsedCredit(usedStock).setClosingCredit(currentClosingStock);

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

        clientRepository.save(client);
        return true;
    }

    @Override
    public List<TransactionRecharge> getRechargeHistory(String clientId) throws Exception {
        return transactionRechargeRepository.findByClientId(clientId);
    }
}
