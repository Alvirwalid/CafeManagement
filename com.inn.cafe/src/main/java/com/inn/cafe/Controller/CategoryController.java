package com.inn.cafe.Controller;


import com.inn.cafe.POJO.Category;
import com.inn.cafe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService service;


    @GetMapping("/all")
    public ResponseEntity<List<Category>>getAllCategory(@RequestParam(required = false) String filterData){
        return  service.getAllcategory(filterData);
    }

    @PostMapping("/add")
    public  ResponseEntity<String>addAll(@RequestBody Map<String,String>requestMap){
        return service.addCategory(requestMap);
    }

    @PutMapping("/update")
    public  ResponseEntity<String>update(@RequestBody Map<String,String>requestMap){

        return  service.updatecategory(requestMap);
    }


}
