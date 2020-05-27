package com.quick.sms.serviceimpl;

import com.quick.sms.documents.*;
import com.quick.sms.dto.authentication.ChangePasswordByOtpDto;
import com.quick.sms.dto.authentication.ChangePasswordDto;
import com.quick.sms.dto.authentication.ForgotPasswordDto;
import com.quick.sms.dto.authentication.ResetPasswordDto;
import com.quick.sms.dto.request.usercreation.SuperAdminCreation;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.enums.UserType;
import com.quick.sms.exception.ConflictException;
import com.quick.sms.exception.IdNotFoundException;
import com.quick.sms.exception.InvalidParameterException;
import com.quick.sms.repository.*;
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
public class ClientServiceImpl implements ClientService {

//    @Autowired
//    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    PricingRepository pricingRepository;

    @Autowired
    OtpRepository otpRepository;

    //@Autowired
    //QuickSmsService quickSmsService;

    @Autowired
    PricingBundleRepository pricingBundleRepository;

    @Autowired
    PricingService pricingService;


    private List<Route> getRoutesFromIdList(List<String> routIds) throws IdNotFoundException {
        //String[] idsArr = routIds.split(",");
        List<String> filterIdList = routIds;
        List<Route> gatewayList = routeRepository.findAll();

        List<Route> filteredGateWayList = gatewayList.stream()
                .filter(gateway -> filterIdList.contains(gateway.getId()))
                .collect(Collectors.toList());

        if(filteredGateWayList == null) throw new IdNotFoundException("Invalid Route Id Passed");
        if(filteredGateWayList.size() == 0)throw new IdNotFoundException("Invalid Route Id Passed");
        return filteredGateWayList;
    }
    private Pricing getPricingDetails(String id) throws IdNotFoundException{
        return pricingRepository.findById(id).orElseThrow(()->new IdNotFoundException("Price Id is not valid"));
    }
    private boolean sendMessage(String phone, String message){
        // Step2: Send OTP to provided phone number
        Map<String, String> payload = new HashMap<>();
        payload.put("smsGateway", Constants.OTP_SMS_GATEWAY);
        payload.put("senderId", Constants.OTP_SMS_SENDER_ID);
        payload.put("countryCode", Constants.OTP_SMS_COUNTRY_CODE);
        payload.put("recipients", phone);
        payload.put("delimiter", ",");
        //payload.put("removeDuplicate", "false");
        payload.put("messageType", Constants.OTP_SMS_MESSGE_TYPE);
        payload.put("message", message);
        payload.put("unSubscribeMessage", Constants.OTP_SMS_UNSUBSCRIBE_MESSAGE);
        InputRequest inputRequest = new InputRequest().setPayload(payload);
        //quickSmsService.sendQuickSMS(authentication, inputRequest, Constants.OTP_SMS_USER_ID);
        return true;
    }
    private Client createSuperAdmin(SuperAdminCreation requestObj) throws Exception {
        List<Route> routes = routeRepository.findAll();
        Client superAdmin = new Client()
                .setUserName(requestObj.getUsername())
                .setUserType(UserType.SUPER_ADMIN.toString())
                .setName(requestObj.getName())
                .setPassword(requestObj.getPassword())
                .setAssignRoute(routes);
        //.setRoles(requestObj.getRoles());
        return clientRepository.save(superAdmin);
    }

    private boolean filterInput(UserCreationDto input) throws Exception{
        // Validate UserType
        List<String> USER_TYPES = Arrays.asList("SUPER_ADMIN", "ADMIN", "RESELLER", "CLIENT");
        List<String> CREDIT_TYPES = Arrays.asList("PREPAID", "POSTPAID");
        List<String> CREDIT_DEDUCTION_TYPES = Arrays.asList("SUBMIT", "DELIVERY");


        if(!USER_TYPES.contains(input.getUserType().toUpperCase())) throw new InvalidParameterException("UserType must be anyone of [\"SUPER_ADMIN\", \"ADMIN\", \"RESELLER\", \"CLIENT\"]");
        if(!CREDIT_TYPES.contains(input.getCreditType().toUpperCase())) throw new InvalidParameterException("CreditType must be anyone of [\"PREPAID\", \"POSTPAID\"]");
        if(!CREDIT_DEDUCTION_TYPES.contains(input.getCreditDeductionType().toUpperCase())) throw new InvalidParameterException("CreditType must be anyone of [\"SUBMIT\", \"DELIVERY\"]");

        return true;
    }

    private Client createUser(UserCreationDto requestObj) throws Exception {
        filterInput(requestObj);
        String parentUserType = "" ;
        if(requestObj.getUserType().toUpperCase().equals("ADMIN")) parentUserType = "SUPER_ADMIN";
        if(requestObj.getUserType().toUpperCase().equals("RESELLER")) parentUserType = "ADMIN";
        if(requestObj.getUserType().toUpperCase().equals("CLIENT")) parentUserType = "RESELLER";

        // Step1: Check parentId is valid or not
        System.out.println("================================CREATOR===========================================");
        System.out.println("USER_TYPE       : "+requestObj.getUserType());
        System.out.println("CREATOR_ID      : "+requestObj.getCreatorId());
        System.out.println("PARENT_USER_TYPE: "+parentUserType);
        System.out.println("===========================================================================");
        Optional<Client> clientOptional = clientRepository.findByIdAndUserType(requestObj.getCreatorId(), parentUserType);
        clientOptional.orElseThrow(()->new IdNotFoundException("Invalid Creator Id"));
        Client creator = clientOptional.get();

        // Step: Check whether userName is duplicated or not
        Optional<Client> userNameOptional = clientRepository.findByUserName(requestObj.getUsername());
       if(userNameOptional.isPresent())throw new ConflictException("Username is already available, Please try different username");

        // Step2: Check provided routeId is valid or not
        List<Route> routes = getRoutesFromIdList(requestObj.getRouteIdList());

        // Step3: Check whether is prepaid or postpaid
        float creditLimit = 0.0f;
        if(requestObj.getCreditType().toUpperCase().equals("POSTPAID")){
            if(requestObj.getCreditLimit() == 0.0) throw new InvalidParameterException("Credit limit should not be 0 for POSTPAID user");
            creditLimit = requestObj.getCreditLimit();
        }

        // Step4: If Dropping applicable then dropping percentage must need to provide
        if(requestObj.isApplyDropping()){
            if(requestObj.getDroppingPercentage() <1.0) throw new InvalidParameterException("Drooping percentage must be more than one");
        }

        // Step5: Check provided price is valid or not
        PricingPlan pricingPlan = null;
        PricingBundle pricingBundle = null;
        if(requestObj.isBundlePriceApplicable()){
            Optional<PricingBundle> bundle = pricingBundleRepository.findById(requestObj.getBundlePriceId());
            if(bundle.isPresent()) pricingBundle = bundle.get();
        }else{
            PricingPlan pricingObj = pricingService.findByPricingId(requestObj.getPricingId());
            if(pricingObj.getPriceInPaisa() != requestObj.getPricingAmount())
                pricingPlan = pricingService.findOrCreate(new PricingPlan(requestObj.getCreatorId(), requestObj.getPricingAmount(), pricingObj.getPlanName(), pricingObj.getGstPercentage()));
            else throw new InvalidParameterException("Invalid PriceAmount");
        }

        Client client = new Client().build(requestObj, requestObj.getUserType(), routes, pricingPlan, pricingBundle, creator);
        return clientRepository.save(client);
    }

    @Override
    public Client createClient(UserCreationDto dto) throws Exception{
        if(dto.getUserType().toLowerCase().equals("superadmin")){
            SuperAdminCreation superAdminCreation =  new SuperAdminCreation(dto.getUsername(), dto.getName(), dto.getPassword());
            return createSuperAdmin(superAdminCreation);
        }else{
            return createUser(dto);
        }
    }

    @Override
    public Client updateClient(InputRequest inputRequest) throws Exception {
        Map<String, String> payload = inputRequest.getPayload();
        Optional<Client> oldClientOptional = clientRepository.findById(payload.get("id"));
        oldClientOptional.orElseThrow(()->new IdNotFoundException("Invalid Client ID"));
        Client oldClient = oldClientOptional.get();

        if(payload.containsKey("firstName")) oldClient.setName(payload.get("name"));
        if(payload.containsKey("email")) oldClient.setName(payload.get("name"));
        if(payload.containsKey("userName")) oldClient.setName(payload.get("userName"));
        if(payload.containsKey("password")) oldClient.setName(payload.get("password"));//passwordEncoder.encode(payload.get("password")
        if(payload.containsKey("phoneNumber")) oldClient.setName(payload.get("phoneNumber"));//passwordEncoder.encode(payload.get("password")
        if(payload.containsKey("company")) oldClient.setName(payload.get("company"));
        if(payload.containsKey("gst")) oldClient.setName(payload.get("gst"));
        if(payload.containsKey("website")) oldClient.setName(payload.get("website"));
        if(payload.containsKey("gst")) oldClient.setName(payload.get("gst"));
        if (payload.containsKey("billAddress")) oldClient.getAddress().setAddress(payload.get("billAddress"));
        if (payload.containsKey("country")) oldClient.getAddress().setCountry(payload.get("country"));
        if (payload.containsKey("state")) oldClient.getAddress().setState(payload.get("state"));
        if (payload.containsKey("city")) oldClient.getAddress().setCity(payload.get("city"));
        if (payload.containsKey("postalCode")) oldClient.getAddress().setPostalCode(payload.get("postalCode"));

       return clientRepository.save(oldClient);
    }

    @Override
    public Response loginClient(LoginRequest loginRequest) throws Exception{
        System.out.println("==================LOGIN=========================");
        System.out.println(loginRequest);
        System.out.println("==================LOGIN=========================");
        Optional<Client> clientOptional = clientRepository.findByUserNameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if(clientOptional.isPresent()) {

            Map<String, String> response = new HashMap<>();
            response.put("message", "Login Success");
            response.put("id", clientOptional.get().getId());
            response.put("userType", clientOptional.get().getUserType());
            response.put("userType", clientOptional.get().getUserType());


            return new Response(true, 200, response, "jhgjgjgjgjgjgjjfhfhfhfhfhfhf");
        }
        return new Response(false, 400, "Login fail", null);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<Client> getAllClientsByUserType(String userType) {
        return clientRepository.findByUserType(userType);
    }

    @Override
    public Client getClientByIdAndType(String userId, String userType) throws Exception {
        return clientRepository.findByIdAndUserType(userId, userType).orElseThrow(()->new InvalidParameterException("Invalid UserId or UserType"));
    }

    @Override
    public Client getClientById(String userId) throws Exception {
        return clientRepository.findById(userId).orElseThrow(()->new IdNotFoundException("Invalid UserId"));
    }

    @Override
    public List<SupportTicket> getClientSupportTickets(String clientId) {
        return null;
    }

    @Override
    public List<Invoice> getClientInvoices(String clientId) {
        return null;
    }

    @Override
    public List<SmsTransaction> getClientSmsTransactions(String clientId) {
        return null;
    }

    @Override
    public void deleteClient(String clientId) {

    }

    @Override
    public void updateClientSmsLimit() {

    }

    @Override
    public void updateStatus(String clientId, String status) {

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


}
