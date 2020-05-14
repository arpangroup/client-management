package com.quick.sms.vo;

import java.util.Map;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InputRequest {
    private Map<String, String> headers;
    private String requestType;
    private Map<String, String> payload;
    private Map<String, Object> data;
  
}
