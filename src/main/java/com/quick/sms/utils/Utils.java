package com.quick.sms.utils;

import com.google.gson.Gson;
import com.quick.sms.vo.InputRequest;

public class Utils {
	
	private static Gson gson;
	
	public static synchronized Gson getGson() {
		if(null == gson) {
			gson = new Gson();
		}
		return gson;
	}
	
	
	public static InputRequest getObjectFromString(String request){
		try {
			return getGson().fromJson(request, InputRequest.class);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new SmsPortalGenException("Invalid input provided");
		}
	}
	
	public static Integer getNumber(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
