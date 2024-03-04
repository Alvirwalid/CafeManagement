package com.inn.cafe.rest;


import com.inn.cafe.POJO.Category;
import com.inn.cafe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    @GetMapping("/all")
    public ResponseEntity<List<Category>>getAllCategory(){
        return  categoryService.getAllcategory();
    }


}
