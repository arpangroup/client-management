package com.quick.sms.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RouteResponse {
    private String id;
    private String routeName;

    public RouteResponse(String id, String routeName) {
        this.id = id;
        this.routeName = routeName;
    }
}


