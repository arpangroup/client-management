package com.quick.sms.exception;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ApiError {
	private Date timestamp = new Date();
	private HttpStatus status;
	private String error;
	//private String exception;
	private String message;
	private String path;
	//private String debugMessage;
	//private List<ApiSubError> subErrors;

	private ApiError(){
		//SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		//String dateString = format.format( new Date()   );
		//Date   date       = format.parse ( dateString);

		timestamp = new Date();
	}
	ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.error = ex.getLocalizedMessage();
	}

	//Fields Constructor
	public ApiError(HttpStatus status, String path, Throwable ex) {
		super();
		timestamp = new Date();
		this.status = status;
		this.path = path;
		this.error = ex.getLocalizedMessage();
		this.message = ex.getMessage();
	}

	//Fields Constructor
	public ApiError(HttpStatus status, String path, Throwable ex, String details) {
		super();
		timestamp = new Date();
		this.status = status;
		this.path = path;
		this.error = ex.getLocalizedMessage();
		this.message = details;
	}
}
