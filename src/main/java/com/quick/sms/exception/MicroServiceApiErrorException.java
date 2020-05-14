package com.quick.sms.exception;

public class MicroServiceApiErrorException extends Exception {

	private static final long serialVersionUID = 3L;

	public MicroServiceApiErrorException(String message) {
		super(message);
	}
}
