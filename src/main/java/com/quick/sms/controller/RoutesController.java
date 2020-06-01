package com.quick.sms.controller;

import java.util.ArrayList;
import java.util.List;

import com.quick.sms.dto.response.RouteResponse;
import com.quick.sms.repository.ClientRepository;
import com.quick.sms.repository.RouteRepository;
import com.quick.sms.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quick.sms.documents.Route;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.vo.InputRequest;
import com.quick.sms.vo.Response;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/routes")
@CrossOrigin(origins="*")
@Slf4j
public class RoutesController {
	
//	@Autowired
//	RouteService userService;

	@Autowired
	RouteRepository routeRepository;

	@Autowired
	RouteService routeService;

	@Autowired
	ClientRepository clientRepository;

	@ApiIgnore
	@PostMapping("add-route")
	public ResponseEntity<Response> addUser(Authentication authentication, @RequestBody InputRequest request){
		Response response = new Response();
		log.info("loggedin user :: {} ",authentication.getName());
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}

	@ApiIgnore
	@PostMapping("update-route")
	public ResponseEntity<Response> modifyGateway(Authentication authentication,@RequestBody InputRequest request){
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiIgnore
	@GetMapping("delete-route/{gatewayId}")
	public ResponseEntity<Response> deleteRoute(Authentication authentication, @PathVariable String gatewayId){
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiIgnore
	@GetMapping("fetch-routes")
	public ResponseEntity<Response> fetchGateways(Authentication authentication){
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiIgnore
	@GetMapping("fetch-active-routes")
	public ResponseEntity<Response> fetchActiveGateways(Authentication authentication){
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("")
	public List<RouteResponse> getAllRoutes(){
		return routeService.getAllRoutes();
	}

	@ApiIgnore
	@GetMapping("/{clientId}")
	public List<Route> getAllRoutes(@PathVariable("clientId") String clientId){
		List<Route> routes = new ArrayList<>();
		try{
			routes = clientRepository.findById(clientId).get().getAssignRoute();
		}catch(Exception e){
			//
		}
		return routes;
	}


}
