package com.quick.sms;

import com.github.javafaker.Faker;
import com.quick.sms.documents.Client;
import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.PricingPlan;
import com.quick.sms.documents.Route;
import com.quick.sms.dto.request.usercreation.UserCreationDto;
import com.quick.sms.repository.RouteRepository;
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
        UserCreationDto userCreationDto = new UserCreationDto()
                .setUserType("superadmin")
                .setUsername(faker.name().username())
                .setName(faker.name().fullName())
                .setPassword("password");
        return clientService.createClient(userCreationDto);
    }
    private void createPricingBySuperAdmin(Client superAdmin) throws Exception{
        PricingPlan pricingPlan1 = new PricingPlan(superAdmin.getId(), 0.25f, "Basic Plan1", 18.0f);
        PricingPlan pricingPlan2 = new PricingPlan(superAdmin.getId(), 0.20f, "Basic Plan2", 18.0f);
        PricingPlan pricingPlan3 = new PricingPlan(superAdmin.getId(), 0.18f, "Gold Plan", 18.0f);
        PricingPlan pricingPlan4 = new PricingPlan(superAdmin.getId(), 0.10f, "Premium Plan", 18.0f);

        pricingService.findOrCreate(pricingPlan1);
        pricingService.findOrCreate(pricingPlan2);
        pricingService.findOrCreate(pricingPlan3);
        pricingService.findOrCreate(pricingPlan4);
    }
    private void createBundleBySuperAdmin(Client superAdmin) throws Exception{
        PricingBundle bundle1 = new PricingBundle(100, 1000, 0.25f, 18.0f, superAdmin.getId());
        PricingBundle bundle2 = new PricingBundle(1000, 5000, 0.20f, 18.0f, superAdmin.getId());
        PricingBundle bundle3 = new PricingBundle(5000, 10000, 0.18f, 18.0f, superAdmin.getId());
        PricingBundle bundle4 = new PricingBundle(10000, 20000, 0.16f, 18.0f, superAdmin.getId());

        pricingService.findOrCreate(bundle1);
        pricingService.findOrCreate(bundle2);
        pricingService.findOrCreate(bundle3);
        pricingService.findOrCreate(bundle4);
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
//        createRoutes();
//        Client superAdmin = createSuperAdmin(); // ID: 5ebc2fc9df8b1b6b98fdb83e
//        createPricingBySuperAdmin(superAdmin);
//        createBundleBySuperAdmin(superAdmin);


//        Client admin1 = createClient("ADMIN", ROUTE_PROMOTIONAL, false, "5ebc9d1d722e7f531ca24003", "5ebc9d1d722e7f531ca24002");
//        Client admin2 = createClient("ADMIN", ROUTE_TRANSACTIONAL, false, "5ebc9d1d722e7f531ca24004", "5ebc9d1d722e7f531ca24002");
//        Client admin3 = createClient("ADMIN", ROUTE_OTP, false, "5ebc9d1d722e7f531ca24004", "5ebc9d1d722e7f531ca24002");

//        Client reseller1 = createClient("RESELLER", ROUTE_TRANSACTIONAL, true, "5ebc9d1e722e7f531ca24007", admin1.getId());

//        Client client1 = createClient("CLIENT", ROUTE_PROMOTIONAL, true, "5ebc9d1e722e7f531ca24007", reseller1.getId());

    }
}
