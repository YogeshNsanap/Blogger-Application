package com.brainworks.rest.exceptions;

import com.brainworks.rest.payloads.response.ApiResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseStatus> resorceNotFoundExceptionHandler(ResourceNotFoundException exception){
        String msg= exception.getMessage();
        ApiResponseStatus apiResponseStatus1 =new ApiResponseStatus(msg,false);
        return new ResponseEntity<ApiResponseStatus>(apiResponseStatus1, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        Map <String,String> response = new HashMap<>();
        exception.getBindingResult().getAllErrors()
                .forEach ((error) -> {
                    String fieldName = ((FieldError) error).getField ();
                    String msg = error.getDefaultMessage ();
                    response.put (fieldName, msg);
                });
        return new ResponseEntity<> (response, HttpStatus.BAD_REQUEST);
    }
}
