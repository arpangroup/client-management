package com.quick.sms.utils;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {

   private Boolean status;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
   private LocalDateTime timestamp;
   private String message;
//   private String debugMessage;
//   private List<String> subErrors;

   private ApiError() {
       timestamp = LocalDateTime.now();
   }
   ApiError(Boolean status) {
       this();
       this.status = status;
   }
   ApiError(Boolean status, Throwable ex) {
       this();
       this.status = status;
       this.message = "Unexpected error";
//       this.debugMessage = ex.getLocalizedMessage();
   }
   ApiError(Boolean status, String message, Throwable ex) {
       this();
       this.status = status;
       this.message = message;
//       this.debugMessage = ex.getLocalizedMessage();
   }
}