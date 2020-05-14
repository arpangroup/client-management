package com.quick.sms.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.quick.sms.documents.Route;
import com.quick.sms.utils.SmsPortalGenException;
import com.quick.sms.utils.Utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RouteService {
	@Autowired
	MongoTemplate mongoTemplate;
	
    @SneakyThrows
	public Boolean addRoute(Map<String, String> payload, Authentication authentication) {
		//UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		String json = Utils.getGson().toJson(payload);
		Route gateway = Utils.getGson().fromJson(json, Route.class);
		gateway.setSrcnpi(0);
		gateway.setSrcton(5);
		gateway.setDestnpi(1);
		gateway.setDestton(1);
		gateway.setDefaultsenderaddress("SMSTKE");
		gateway.setAllownumeric(true);
		gateway.setUsedefaultsender(false);
		gateway.setSystemtype("smpp");
		gateway.setCreationdate(new Date());
		//gateway.setCreatedBy(user.getUsername());
		gateway.setCreatedOn(LocalDateTime.now());
		mongoTemplate.save(gateway);
		return true;
	}

    @SneakyThrows
	public Boolean updateGateway(Map<String, String> payload, Authentication authentication) {
		try {
		//UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		if(StringUtils.isEmpty(payload.get("routeId")) ) {
			throw new SmsPortalGenException("routeId is mandatory to update details");
		}
		Query query = new Query();
        //query.addCriteria(Criteria.where("id").is(payload.get("routeId")).and("createdBy").is(user.getUsername()));
		Route gateway = mongoTemplate.findOne(query, Route.class);
		if(null == gateway) {
			throw new SmsPortalGenException("Invalid route id provided");
		}
		if(StringUtils.isNotBlank(payload.get("port"))){
			gateway.setPort(Integer.parseInt(payload.get("port")));
		}
		if(StringUtils.isNotBlank(payload.get("hostname"))){
			gateway.setHostname(payload.get("hostname"));
		}
		if(StringUtils.isNotBlank(payload.get("routeName"))){
			gateway.setRouteName(payload.get("routeName"));
		}
		if(StringUtils.isNotBlank(payload.get("systemId"))){
			gateway.setSystemId(payload.get("systemId"));
		}
		if(StringUtils.isNotBlank(payload.get("password"))){
			gateway.setPassword(payload.get("password"));
		}
		if(StringUtils.isNotBlank(payload.get("transmitter"))){
			gateway.setTransmitter(Utils.getNumber(payload.get("transmitter")));
		}
		if(StringUtils.isNotBlank(payload.get("transceiver"))){
			gateway.setTransceiver(Utils.getNumber(payload.get("transceiver")));
		}
		if(StringUtils.isNotBlank(payload.get("receiver"))){
			gateway.setReceiver(Utils.getNumber(payload.get("receiver")));
		}
		if(StringUtils.isNotBlank(payload.get("status"))){
			gateway.setStatus(Utils.getNumber(payload.get("status")));
		}
		//gateway.setUpdatedBy(user.getUsername());
		gateway.setUpdatedOn(LocalDateTime.now());
		mongoTemplate.save(gateway);
		} catch(Exception ex) {
			log.error("Error while updating Route", ex);
			throw new SmsPortalGenException("Error while adding route"); 
		}
		return true;
	}

    /**
     * 
     * @param authentication
     * @param routeId
     * @return
     */
    @SneakyThrows
	public Boolean deleteRoute( Authentication authentication, String routeId) {
		log.info("Deleting Route {} ", routeId);
		//UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Route route = mongoTemplate.findById(routeId, Route.class);
//		if(!route.getCreatedBy().equals(user.getUsername())) {
//			throw new SmsPortalGenException("Invalid route id");
//		}
		mongoTemplate.remove(route);
		return true;
	}

	public List<Route> fetchGateways(Authentication authentication, boolean fetchActive) {
		log.info("Fetching Routes" );
		//UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
		Query query = new Query();
		query.with(Sort.by(Direction.DESC, "createdOn"));
		Criteria criteriaDefinition = new Criteria();
		if(fetchActive) {
			criteriaDefinition.and("transmitter").gt(0);
		}
		query.addCriteria(criteriaDefinition);
		List<Route> gateway = mongoTemplate.find(query, Route.class);
		return gateway;
	}

}
