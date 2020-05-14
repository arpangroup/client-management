package com.quick.sms.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	// MethodArgumentNotValidException
		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                      HttpHeaders headers, HttpStatus status, WebRequest request) {

			ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,"From MethodArgumentNotValid Exception in GEH", ex);

			return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
		}

		// HttpRequestMethodNotSupportedException
		@Override
		protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {

			ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED,
					"From HttpRequestMethodNotSupportedException in GEH - Method Not allowed", ex);

			return new ResponseEntity<>(apiError, HttpStatus.METHOD_NOT_ALLOWED);

		}

		//EntityNotFoundException
//		@ExceptionHandler(EntityNotFoundException.class)
//		protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
//			ApiError apiError = new ApiError( HttpStatus.NOT_FOUND);
//			apiError.setMessage("Entity not found");
//
//			return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
//
//		}


		//AuthenticationErrorException
		@ExceptionHandler(AuthenticationErrorException.class)
		protected ResponseEntity<Object> handleAuthenticationException(AuthenticationErrorException ex, WebRequest request) {
			ApiError apiError = new ApiError( HttpStatus.UNAUTHORIZED, request.getDescription(false), ex);
			apiError.setMessage("Authentication Failed");

			return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
		}

		// IdNotFoundException
		@ExceptionHandler(IdNotFoundException.class)
		public final ResponseEntity<Object> handleUserNameNotFoundException(IdNotFoundException ex,
                                                                            WebRequest request) {
			ApiError apiError = new ApiError( HttpStatus.NOT_FOUND, request.getDescription(false), ex);
			apiError.setMessage("Invalid ID Passed");

			return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

		}

		// IdNotFoundException
		@ExceptionHandler(InvalidParameterException.class)
		public final ResponseEntity<Object> handleUserInvalidParameterException(InvalidParameterException ex, WebRequest request) {
			ApiError apiError = new ApiError( HttpStatus.NOT_FOUND, request.getDescription(false), ex);
			apiError.setMessage("Invalid Parameter Passed");

			return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

		}




		//ConflictException
		@ExceptionHandler(ConflictException.class)
		protected ResponseEntity<Object> handleConflictException(ConflictException ex,
                                                                 WebRequest request) {
			ApiError apiError = new ApiError( HttpStatus.CONFLICT, request.getDescription(false), ex);
			apiError.setMessage("Conflicted Data Found");

			return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);

		}





		// ParameterNotFoundException
		@ExceptionHandler(ParameterNameNotFoundException.class)
		public final ResponseEntity<Object> parameterNameNotFoundException(ParameterNameNotFoundException ex,
                                                                           WebRequest request) {
			ApiError apiError = new ApiError( HttpStatus.NOT_FOUND, request.getDescription(false), ex);
			apiError.setMessage("Required Parameter(s) Not Found");

			return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

		}

	// ParameterNotFoundException
	@ExceptionHandler(MicroServiceApiErrorException.class)
	public final ResponseEntity<Object> microServiceApiErrorException(MicroServiceApiErrorException ex,
                                                                      WebRequest request) {
		ApiError apiError = new ApiError( HttpStatus.SERVICE_UNAVAILABLE, request.getDescription(false), ex, "MicroService Internal API error");
		apiError.setMessage("MicroService Call Failed");

		return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);

	}


	// ConstraintViolationException
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                           WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,request.getDescription(false), ex);
		apiError.setMessage("Constraint violation error");

		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}



	// PaymentRequiredException
	@ExceptionHandler(PaymentRequiredException.class)
	public final ResponseEntity<Object> handlePaymentRequiredException(PaymentRequiredException ex,
                                                                       WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.PAYMENT_REQUIRED,request.getDescription(false), ex);
		apiError.setMessage("Payment is not done");

		return new ResponseEntity<>(apiError, HttpStatus.PAYMENT_REQUIRED);
	}

	// PreConditionFailedException
	@ExceptionHandler(PreConditionFailedException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(PreConditionFailedException ex,
                                                                           WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.PRECONDITION_FAILED,request.getDescription(false), ex);
		apiError.setMessage("Pre-condition Failed");

		return new ResponseEntity<>(apiError, HttpStatus.PRECONDITION_FAILED);
	}

	// UnSupportedMediaTypeException
	@ExceptionHandler(UnSupportedMediaTypeException.class)
	public final ResponseEntity<Object> handleUnSupportedMediaTypeException(UnSupportedMediaTypeException ex,
                                                                            WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,request.getDescription(false), ex);
		apiError.setMessage("Please provide supported MediaType");

		return new ResponseEntity<>(apiError, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}




}
