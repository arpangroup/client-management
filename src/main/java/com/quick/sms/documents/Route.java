package com.quick.sms.documents;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.quick.sms.dto.response.RouteResponse;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the smpp_gateway database table.
 * 
 */
@Getter
@Setter
@Document
@Accessors(chain = true)
//@Builder
public class Route implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String routeName;
    private String hostname;
    private Integer port;
    private String systemId;
    private String password;
    private Integer transmitter;
    private Integer transceiver;
    private Integer receiver;
    private String defaultsenderaddress;
    private Boolean allownumeric;
    private Boolean usedefaultsender;
    private String systemtype;
    private Date creationdate;
    private Integer status;
    private Integer srcton;
    private Integer srcnpi;
    private Integer destton;
    private Integer destnpi;
    private String type;

	public String createdBy;

	public LocalDateTime createdOn;

	public String updatedBy;

	private LocalDateTime updatedOn;


    public Route(String routeName) {
        this.routeName = routeName;
    }

    public static RouteResponse build(Route route){
        return new RouteResponse(route.id, route.routeName);
    }

}