package com.quick.sms;

import com.github.javafaker.Faker;
import com.quick.sms.documents.*;
import com.quick.sms.dto.request.price.Bundle;
import com.quick.sms.dto.request.price.BundlePriceRequest;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.dto.response.ClientDetailsResponse;
import com.quick.sms.repository.*;
import com.quick.sms.service.ClientService;
import com.quick.sms.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DBCommandLineRunner implements CommandLineRunner {
    private Faker faker = new Faker(new Locale("en-IND"));

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    PricingService pricingService;

    @Autowired
    PricingPlanRepository pricingPlanRepository;
    @Autowired
    PricingBundleRepository pricingBundleRepository;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;

    List<String> ROUTE_PROMOTIONAL = Arrays.asList("1");
    List<String> ROUTE_TRANSACTIONAL = Arrays.asList("2");
    List<String> ROUTE_OTP = Arrays.asList("3");

    private void createRoutes(){
        Route route1 = new Route("PROMOTIONAL").setId("1");
        Route route2 = new Route("TRANSACTIONAL").setId("2");
        Route route3 = new Route("OTP").setId("3");

        routeRepository.save(route1);
        routeRepository.save(route2);
        routeRepository.save(route3);
    }
    private Client createSuperAdmin() throws Exception{
        List<Route> routes = routeRepository.findAll();
        Client superAdmin = new Client()
                .setId("1")
                .setUserName("nani")
                .setUserType("SUPER_ADMIN")
                .setName("Nagendra Nagarathi")
                .setPassword("password")
                .setAssignRoute(routes);
        return clientRepository.save(superAdmin);
    }
    private void createPricingBySuperAdmin(Client superAdmin) throws Exception{
        PricingPlan pricingPlan1 = new PricingPlan(superAdmin.getId(), 25, "Basic Plan1", 18.0f);
        PricingPlan pricingPlan2 = new PricingPlan(superAdmin.getId(), 20, "Basic Plan2", 18.0f);
        PricingPlan pricingPlan3 = new PricingPlan(superAdmin.getId(), 18, "Gold Plan", 18.0f);
        PricingPlan pricingPlan4 = new PricingPlan(superAdmin.getId(), 10, "Premium Plan", 18.0f);

        pricingPlan1.setId("demo_p1");
        pricingPlan2.setId("demo_p2");
        pricingPlan3.setId("demo_p3");
        pricingPlan4.setId("demo_p4");

        List<PricingPlan> pricingPlanList = Arrays.asList(pricingPlan1, pricingPlan2, pricingPlan3, pricingPlan4);
        pricingPlanRepository.saveAll(pricingPlanList);

//        pricingService.findOrCreate(pricingPlan1);
//        pricingService.findOrCreate(pricingPlan2);
//        pricingService.findOrCreate(pricingPlan3);
//        pricingService.findOrCreate(pricingPlan4);
    }
    private void createBundleBySuperAdmin(Client superAdmin) throws Exception{
        Bundle bundle1 = new Bundle(100, 1000, 0.25f, 0.18f, false);
        Bundle bundle2 = new Bundle(1000, 5000, 0.25f, 0.18f, false);
        Bundle bundle3 = new Bundle(5000, 10000, 0.25f, 0.18f, false);
        Bundle bundle4 = new Bundle(10000, 20000, 0.25f, 0.18f, false);

        List<Bundle> bundleList = Arrays.asList(bundle1, bundle2, bundle3, bundle4);
        BundlePriceRequest plan1 = new BundlePriceRequest("Basic Plan", bundleList, superAdmin.getId());

        BundlePriceRequest plan2 = new BundlePriceRequest("Premium Plan", bundleList, superAdmin.getId());

        pricingService.createBundlePrice(plan1);
        pricingService.createBundlePrice(plan2);

    }



    private ClientDetailsResponse createClient(String userType, List<String> routes, boolean isBundleApplicable, String pricingId, String creatorId) throws Exception{
        UserCreationDto userCreationDto = new UserCreationDto();
        if(isBundleApplicable) userCreationDto.setBundlePriceId(pricingId);
        else {userCreationDto.setPricingId(pricingId).setPricingAmount(10);}

        userCreationDto.setUserType(userType)
            .setName(faker.name().fullName())
            .setEmail("")
            .setUsername(faker.name().username())
            .setPassword("password")
            .setPhoneNumber(faker.phoneNumber().phoneNumber())
            .setCompany(faker.company().name())
            .setCompanyType("Pvt Ltd.")
            .setRouteIdList(routes)
            .setGstno("123456")
            .setGstInclusive(true)
            .setAccountType("PREPAID")
            .setCreditDeductionType("SUBMIT")
            .setCreditLimit(0.0f)
            .setApplyDndReturn(true)
            .setApplyDropping(true)
            .setDroppingPercentage(10)
            .setDroppingAccessApplicableToChild(true)
            .setBundlePriceApplicable(isBundleApplicable)
            .setCreatorId(creatorId);

        return clientService.createClient(userCreationDto);
    }

    @Override
    public void run(String... args) throws Exception {
        /*
        createRoutes();
        Client superAdmin = createSuperAdmin(); // ID: 5ebc2fc9df8b1b6b98fdb83e
        createPricingBySuperAdmin(superAdmin);
        createBundleBySuperAdmin(superAdmin);


        ClientDetailsResponse admin1 = createClient("ADMIN", ROUTE_PROMOTIONAL, false, "demo_p1", superAdmin.getId());
        ClientDetailsResponse admin2 = createClient("ADMIN", ROUTE_TRANSACTIONAL, false, "demo_p2", superAdmin.getId());
        ClientDetailsResponse admin3 = createClient("ADMIN", ROUTE_OTP, false, "demo_p3", superAdmin.getId());

        ClientDetailsResponse reseller1 = createClient("RESELLER", ROUTE_TRANSACTIONAL, true, "demo_p1", admin1.getId());

        ClientDetailsResponse client1 = createClient("CLIENT", ROUTE_PROMOTIONAL, true, "demo_p1", reseller1.getId());
    */

    }
}
