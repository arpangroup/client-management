package com.quick.sms.service;

import com.quick.sms.dto.request.route.RouteRequest;
import com.quick.sms.dto.request.route.UpdateRouteRequest;
import com.quick.sms.dto.response.RouteResponse;

import java.util.List;

public interface RouteService {
    public RouteResponse addRoute(RouteRequest routeRequest) throws Exception;
    public RouteResponse updateRoute(UpdateRouteRequest updateRouteRequest) throws Exception;
    public Boolean deleteRoute(String routeId) throws Exception;
    public List<RouteResponse> getAllRoutes();
}
