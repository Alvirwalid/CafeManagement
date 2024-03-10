package com.inn.cafe.service;

import com.inn.cafe.constant.BaseConstant;
import com.inn.cafe.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import java.util.Map;

public interface ProductService extends BaseConstant {

    ResponseEntity<BaseResponse> addNewProduct(Map<String,String>requestMap);
    ResponseEntity<BaseResponse> getAllProduct();

    ResponseEntity<BaseResponse> getProductById(Integer id);

    ResponseEntity<BaseResponse>update(Map<String,String>requestMap);
    ResponseEntity<BaseResponse>delete(Integer id);
    ResponseEntity<BaseResponse>updateStatus(Map<String,String>requestMap);

}
