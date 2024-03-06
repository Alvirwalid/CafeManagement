package com.inn.cafe.service;

import com.inn.cafe.POJO.Products;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ResponseEntity<String> addNewProduct(Map<String,String>requestMap);
    ResponseEntity<List<Products>> getAllProduct();
}
