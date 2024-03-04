package com.inn.cafe.serviceImpl;

import com.inn.cafe.POJO.Category;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.repository.CategoryRepository;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CategoryServiceImpl implements CategoryService {

@Autowired
    CategoryRepository categoryRepository;
    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<List<Category>> getAllcategory() {

        try{
            return new  ResponseEntity<>(categoryRepository.getAllCategory(),HttpStatus.OK);
        }catch (Exception e){e.printStackTrace();}

        return new  ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addCategory(Map<String,String>requestMap) {


        try {
            if(jwtFilter.isAdmin()){
                if(validateCategoryMap(requestMap,false)){
                    categoryRepository.save(getCategoryFromMap(requestMap,false));
                    return  CafeUtils.getResponseEntity("Category Added Successfully",HttpStatus.OK);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private  Category getCategoryFromMap(Map<String,String>requestMap,Boolean isAdd){
        Category category=new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName("name");
        return  category;
    }

    private  boolean validateCategoryMap(Map<String,String>requestMap,boolean validateId){

        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return  true;
            }else if (!validateId){
                return  true;
            }
        }

        return  false;
    }
}
