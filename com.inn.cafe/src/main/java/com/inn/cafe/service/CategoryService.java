package com.inn.cafe.service;

import com.inn.cafe.POJO.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseEntity<List<Category>>getAllcategory();

    ResponseEntity<String>addCategory(Map<String,String> requestMap);
}
