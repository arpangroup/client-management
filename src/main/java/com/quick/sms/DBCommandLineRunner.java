package com.quick.sms;

import com.github.javafaker.Faker;
import com.quick.sms.documents.*;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.enums.UserType;
import com.quick.sms.repository.*;
import com.quick.sms.service.ClientService;
import com.quick.sms.service.PricingService;
import com.quick.sms.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

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
        PricingPlan pricingPlan1 = new PricingPlan(superAdmin.getId(), 0.25f, "Basic Plan1", 18.0f);
        PricingPlan pricingPlan2 = new PricingPlan(superAdmin.getId(), 0.20f, "Basic Plan2", 18.0f);
        PricingPlan pricingPlan3 = new PricingPlan(superAdmin.getId(), 0.18f, "Gold Plan", 18.0f);
        PricingPlan pricingPlan4 = new PricingPlan(superAdmin.getId(), 0.10f, "Premium Plan", 18.0f);

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
        PricingBundle bundle1 = new PricingBundle(100, 1000, 0.25f, 18.0f, superAdmin.getId());
        PricingBundle bundle2 = new PricingBundle(1000, 5000, 0.20f, 18.0f, superAdmin.getId());
        PricingBundle bundle3 = new PricingBundle(5000, 10000, 0.18f, 18.0f, superAdmin.getId());
        PricingBundle bundle4 = new PricingBundle(10000, 20000, 0.16f, 18.0f, superAdmin.getId());

        bundle1.setId("demo_b1");
        bundle2.setId("demo_b2");
        bundle3.setId("demo_b3");
        bundle4.setId("demo_b4");

        List<PricingBundle> bundleList = Arrays.asList(bundle1, bundle2, bundle3, bundle4);
        pricingBundleRepository.saveAll(bundleList);

//        pricingService.findOrCreate(bundle1);
//        pricingService.findOrCreate(bundle2);
//        pricingService.findOrCreate(bundle3);
//        pricingService.findOrCreate(bundle4);
    }



    private Client createClient(String userType, List<String> routes, boolean isBundleApplicable, String pricingId, String creatorId) throws Exception{
        UserCreationDto userCreationDto = new UserCreationDto();
        if(isBundleApplicable) userCreationDto.setBundlePriceId(pricingId);
        else {userCreationDto.setPricingId(pricingId).setPricingAmount(0.10F);}

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
            .setCreditType("PREPAID")
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
        // RouteId: [{"5ebc2fc8df8b1b6b98fdb83b": "PROMOTIONAL"}, {"5ebc2fc8df8b1b6b98fdb83c": "TRANSACTIONAL"},  {"5ebc2fc8df8b1b6b98fdb83d": "OTP"}]
        createRoutes();
        Client superAdmin = createSuperAdmin(); // ID: 5ebc2fc9df8b1b6b98fdb83e
        createPricingBySuperAdmin(superAdmin);
        createBundleBySuperAdmin(superAdmin);


        Client admin1 = createClient("ADMIN", ROUTE_PROMOTIONAL, false, "demo_p1", superAdmin.getId());
        Client admin2 = createClient("ADMIN", ROUTE_TRANSACTIONAL, false, "demo_p2", superAdmin.getId());
        Client admin3 = createClient("ADMIN", ROUTE_OTP, false, "demo_p3", superAdmin.getId());

        Client reseller1 = createClient("RESELLER", ROUTE_TRANSACTIONAL, true, "demo_b1", admin1.getId());

        Client client1 = createClient("CLIENT", ROUTE_PROMOTIONAL, true, "demo_b2", reseller1.getId());

    }
}
