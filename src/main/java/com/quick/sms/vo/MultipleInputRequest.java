package com.quick.sms.vo;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class MultipleInputRequest {
    private Map<String, String> headers;
    private String requestType;
    private List<Map<String, String>> payload;

}
