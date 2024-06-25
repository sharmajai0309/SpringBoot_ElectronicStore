package com.Jai.electronic.store.ElectronicStore.exception;



import com.Jai.electronic.store.ElectronicStore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        logger.info("Global Exception invoked");
        ApiResponseMessage response= ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND).success(true)
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

    }
    // for method not valid exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        logger.info("MethodArgumentNotValidException invoked");
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
           String message =  objectError.getDefaultMessage();
           String field = ((FieldError)objectError).getField();
            response.put(field,message);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    //Handle BadAptRequest for image uplode
    @ExceptionHandler(BadAptRequest.class)
    public ResponseEntity<ApiResponseMessage> handleBadAptRequest(ResourceNotFoundException ex){
        logger.info("Image bad Api Exception invoked");
        ApiResponseMessage response= ApiResponseMessage.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST).success(false)
                .build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }
}
