package com.inn.cafe.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class CafeUtils {


    public  static ResponseEntity<String>getResponseEntity(String message, HttpStatus httpStatus){
        return new  ResponseEntity<String>("{\"message\":\""+message+"\"}",httpStatus);
    }
}
