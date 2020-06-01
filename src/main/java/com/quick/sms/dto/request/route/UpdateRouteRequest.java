package com.quick.sms.dto.request.route;

import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
public class UpdateRouteRequest {
    private String id;
    private String routeName;
    private String hostname;
    private Integer port;
    private String systemId;
    private String password;
    private Integer transmitter;
    private Integer transceiver;
    private Integer receiver;
    private Integer status;
}
