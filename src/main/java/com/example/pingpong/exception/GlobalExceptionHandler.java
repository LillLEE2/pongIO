package com.example.pingpong.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
       Map<String, String> responseBody = new HashMap<>();
       responseBody.put("error", e.getMessage());
       return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
   }

}