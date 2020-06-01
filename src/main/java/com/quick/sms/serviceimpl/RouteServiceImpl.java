package com.quick.sms.serviceimpl;

import com.quick.sms.documents.PricingBundle;
import com.quick.sms.documents.PricingPlan;
import com.quick.sms.documents.Route;
import com.quick.sms.dto.request.route.RouteRequest;
import com.quick.sms.dto.request.route.UpdateRouteRequest;
import com.quick.sms.dto.response.RouteResponse;
import com.quick.sms.exception.IdNotFoundException;
import com.quick.sms.repository.PricingBundleRepository;
import com.quick.sms.repository.PricingPlanRepository;
import com.quick.sms.repository.RouteRepository;
import com.quick.sms.service.PricingService;
import com.quick.sms.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    RouteRepository routeRepository;

    @Override
    public RouteResponse addRoute(RouteRequest routeRequest) throws Exception {
        return null;
    }

    @Override
    public RouteResponse updateRoute(UpdateRouteRequest updateRouteRequest) throws Exception {
        return null;
    }

    @Override
    public Boolean deleteRoute(String routeId) throws Exception {
        return null;
    }

    @Override
    public List<RouteResponse> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        return routes.stream().map(Route::build).collect(Collectors.toList());
    }
}
