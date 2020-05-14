package com.quick.sms.api;

import java.util.List;

import com.quick.sms.repository.RouteRepository;
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
import com.quick.sms.service.RouteService;
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
	
	@Autowired
	RouteService userService;

	@Autowired
	RouteRepository routeRepository;

	@ApiIgnore
	@PostMapping("add-route")
	public ResponseEntity<Response> addUser(Authentication authentication, @RequestBody InputRequest request){
		Response response = new Response();
		log.info("loggedin user :: {} ",authentication.getName());
		try {
			Boolean result = userService.addRoute(request.getPayload(), authentication);
			response.setResponse(result ? "Gateway added successfully." : "Error while adding gateway");
		} catch (Exception e) {
			 log.error("Error while add gateway", e);
			 throw new SmsPortalGenException(e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}

	@ApiIgnore
	@PostMapping("update-route")
	public ResponseEntity<Response> modifyGateway(Authentication authentication,@RequestBody InputRequest request){
		Response response = new Response();
		Boolean result = userService.updateGateway(request.getPayload(), authentication);
		response.setResponse(result);
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}

	@ApiIgnore
	@GetMapping("delete-route/{gatewayId}")
	public ResponseEntity<Response> deleteRoute(Authentication authentication, @PathVariable String gatewayId){
		Response response = new Response();
		response.setResCode(200);
		try {
			Boolean result = userService.deleteRoute(authentication, gatewayId);
			response.setResponse("Route successfully deleted.");
		} catch(Exception ex){
			log.error("Error while deleting route ", ex);
			throw new SmsPortalGenException(ex.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}

	@ApiIgnore
	@GetMapping("fetch-routes")
	public ResponseEntity<Response> fetchGateways(Authentication authentication){
		Response response = new Response();
		List<Route> list = userService.fetchGateways(authentication, false);
		response.setResponse(list);
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}

	@ApiIgnore
	@GetMapping("fetch-active-routes")
	public ResponseEntity<Response> fetchActiveGateways(Authentication authentication){
		Response response = new Response();
		List<Route> list = userService.fetchGateways(authentication, true);
		response.setResponse(list);
		return new ResponseEntity<>(response, HttpStatus.OK); 
	}

	@GetMapping("")
	public List<Route> getAllRoutes(){
		return routeRepository.findAll();
	}


}
