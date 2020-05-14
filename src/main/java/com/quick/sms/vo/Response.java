package com.quick.sms.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Response {
	private Boolean status = true;
    private int resCode;
    private Object response;
    private String sessionToken;

    public Response(Boolean status, int resCode, Object response, String sessionToken) {
        this.status = status;
        this.resCode = resCode;
        this.response = response;
        this.sessionToken = sessionToken;
    }
}
