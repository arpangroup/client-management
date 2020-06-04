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
    private List<ClientResponse> filterClients(List<Client> clientList){
        List<ClientResponse> clients = new ArrayList<>();
        clientList.forEach(client->{
            String createdBy = clientRepository.findById(client.getCreatedBy()).get().getUserName();
            ClientResponse resp = new ClientResponse(client.getId(), client.getUserType(), client.getName(), client.getUserName(),client.getPhoneNumber(), client.getWallet(), client.getCreateDate(), client.getUpdateDate(), client.getStatus(), createdBy);
            clients.add(resp);
        });
        return clients;

    }

    private boolean filterInput(UserCreationDto input) throws Exception{
        // Validate UserType
        List<String> USER_TYPES = Arrays.asList("SUPER_ADMIN", "ADMIN", "RESELLER", "CLIENT");
        List<String> CREDIT_TYPES = Arrays.asList("PREPAID", "POSTPAID");
        List<String> CREDIT_DEDUCTION_TYPES = Arrays.asList("SUBMIT", "DELIVERY");


        if(!USER_TYPES.contains(input.getUserType().toUpperCase())) throw new InvalidParameterException("UserType must be anyone of [\"SUPER_ADMIN\", \"ADMIN\", \"RESELLER\", \"CLIENT\"]");
        if(!CREDIT_TYPES.contains(input.getAccountType().toUpperCase())) throw new InvalidParameterException("CreditType must be anyone of [\"PREPAID\", \"POSTPAID\"]");
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
        if(requestObj.getAccountType().toUpperCase().equals("POSTPAID")){
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
            if(!bundle.isPresent())throw new InvalidParameterException("Invalid Bundle Id");
            pricingBundle = bundle.get();
        }else{
            PricingPlan pricingObj = pricingService.findByPricingId(requestObj.getPricingId());
            if(pricingObj.getFixedPriceInPaisa() != requestObj.getPricingAmount())
                pricingPlan = pricingService.findOrCreate(new PricingPlan(requestObj.getCreatorId(), requestObj.getPricingAmount(), pricingObj.getPlanName(), pricingObj.getGstPercentage()));
            else throw new InvalidParameterException("Invalid PriceAmount");
        }

        Client client = new Client().build(requestObj, requestObj.getUserType(), routes, pricingPlan, pricingBundle, requestObj.getCreatorId());
        return clientRepository.save(client);
    }

    @Override
    public ClientDetailsResponse createClient(UserCreationDto dto) throws Exception{
        if(dto.getUserType().toLowerCase().equals("superadmin")){
            SuperAdminCreation superAdminCreation =  new SuperAdminCreation(dto.getUsername(), dto.getName(), dto.getPassword());
            return Client.build(createSuperAdmin(superAdminCreation));
        }else{
            return Client.build(createUser(dto));
        }
    }

    /*
    @Override
    public ClientDetailsResponse updateClient(InputRequest inputRequest) throws Exception {
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

       Client client = clientRepository.save(oldClient);
       return Client.build(client);
    }

     */

    @Override
    public ClientDetailsResponse updateClient(UpdateUserDetails payload) throws Exception {
        Optional<Client> oldClientOptional = clientRepository.findById(payload.getClientId());
        oldClientOptional.orElseThrow(()->new IdNotFoundException("Invalid Client ID"));
        Client oldClient = oldClientOptional.get();


        if(payload.getCompanyName() != null) oldClient.setCompany(payload.getCompanyName());
        if(payload.getCompanyType() != null) oldClient.setCompanyType(payload.getCompanyType());

        Address address = oldClient.getAddress();
        address.setCountry(payload.getCountry()).setState(payload.getState()).setAddress(payload.getAddress());
        oldClient.setGstno(payload.getGstNo());
        oldClient.setAddress(address);

        Client client = clientRepository.save(oldClient);
        return Client.build(client);
    }

    @Override
    public List<ClientResponse> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        clients = clients.stream().filter(client -> !client.getUserType().equals("SUPER_ADMIN")).collect(Collectors.toList());// Because first client is SUPER_ADMIN
        return filterClients(clients);
    }

    @Override
    public List<ClientResponse> getAllClientsByUserType(String userType) {
        List<Client> clients = clientRepository.findByUserType(userType);
        return filterClients(clients);
    }

    @Override
    public List<ClientResponse> getAllClientsUnderParentId(String parentId)throws Exception{
        List<Client> clients = clientRepository.findByCreatedBy(parentId);
        return filterClients(clients);
    }

    @Override
    public ClientDetailsResponse getClientByIdAndType(String userId, String userType) throws Exception {
        Client client = clientRepository.findByIdAndUserType(userId, userType).orElseThrow(()->new InvalidParameterException("Invalid UserId or UserType"));
        String createdBy = clientRepository.findById(client.getCreatedBy()).get().getUserName();

        return Client.build(client).setCreatedBy(createdBy);
    }

    @Override
    public ClientDetailsResponse getClientById(String userId) throws Exception {
        Client client = clientRepository.findById(userId).orElseThrow(()->new IdNotFoundException("Invalid UserId"));
        String createdBy = clientRepository.findById(client.getCreatedBy()).get().getUserName();

        return Client.build(client).setCreatedBy(createdBy);
    }

    public Client getClientByClientId(String userId) throws Exception {
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
    public void updateClientSmsLimit() {

    }

    @Override
    public void updateStatus(String clientId, String status) {

    }

}
