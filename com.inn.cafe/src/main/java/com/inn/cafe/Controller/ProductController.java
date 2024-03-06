package com.inn.cafe.Controller;


import com.inn.cafe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService service;
    @PostMapping("/add")
    public ResponseEntity<String>addNewProduct(@RequestBody Map<String,String>requestMap){

        return service.addNewProduct(requestMap);
    }
}
