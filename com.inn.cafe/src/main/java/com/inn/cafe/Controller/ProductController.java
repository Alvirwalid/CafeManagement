package com.inn.cafe.Controller;


import com.inn.cafe.service.ProductService;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/all")

    public  ResponseEntity<List<ProductWrapper>>getAllProducts(){
        return  service.getAllProduct();
    }


    @GetMapping("/get-by-id")
    public  ResponseEntity<ProductWrapper>getProductbyId(@RequestParam Integer id){


//        System.out.println("iddddddddddddd"+id);
        return  service.getProductById(id);
    }

    @PutMapping("/update")
    public  ResponseEntity<String>update (@RequestBody Map<String,String>requestMap){

        System.out.println("bodyyyyyy : "+requestMap);
        return  service.update(requestMap);
    }


    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<String>delete(@PathVariable("id") Integer id){

//        System.out.println("iddddddddd : "+id);
        return  service.delete(id);
    }

    @PutMapping("/updateStatus")
    public  ResponseEntity<String>updateStatus (@RequestBody Map<String,String>requestMap){
        return  service.updateStatus(requestMap);
    }

}
