package com.inn.cafe.service;

import com.inn.cafe.POJO.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseEntity<List<Category>>getAllcategory(String filterData);

    ResponseEntity<String>addCategory(Map<String,String> requestMap);


    ResponseEntity<String>updatecategory(@RequestBody Map<String,String>requestMap);
}
