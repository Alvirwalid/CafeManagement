package com.inn.cafe.utils;

import com.inn.cafe.POJO.auth.AuthenticationResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class CafeUtils {


    public  static ResponseEntity<String>getResponseEntity(String message, HttpStatus httpStatus){
        return new  ResponseEntity<String>("{\"message\":\""+message+"\"}",httpStatus);
    }
    public  static ResponseEntity<AuthenticationResponse>getResponseEntityForAuth(String message, HttpStatus httpStatus){
        return new  ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(message),httpStatus);
    }

}
