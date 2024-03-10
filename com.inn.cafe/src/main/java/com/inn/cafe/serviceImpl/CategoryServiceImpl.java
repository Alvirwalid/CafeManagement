package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.repository.CategoryRepository;
import com.inn.cafe.service.CategoryService;
import com.inn.cafe.utils.BaseResponse;
import com.inn.cafe.utils.CafeUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repo;
    @Autowired
    JwtFilter jwtFilter;


//    @Autowired
    CafeUtils cafeUtils;
    @Override
    public ResponseEntity<BaseResponse> getAllcategory(String filterData) {

        try{
            if(!Strings.isNullOrEmpty(filterData) && filterData.equalsIgnoreCase("true")){

//                System.out.println("True");

                return new  ResponseEntity<>(cafeUtils.generateSuccessResponse(repo.getAllCategory(),"",""),HttpStatus.OK);
            }


//            System.out.println("False");
            return new  ResponseEntity<>(cafeUtils.generateSuccessResponse(repo.findAll(),"",""),HttpStatus.OK);

        }catch (Exception e){
            return new  ResponseEntity<>(cafeUtils.generateErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public ResponseEntity<BaseResponse> addCategory(Map<String,String>requestMap) {


        try {
            if(jwtFilter.isAdmin()){

                if(validateCategoryMap(requestMap,false)){
                    repo.save(getCategoryFromMap(requestMap,false));
                    return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,SAVE_MESSAGE,SAVE_MESSAGE_BN),HttpStatus.OK);
                }else {
                    return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,INVALID_DATA,INVALID_DATA_BN),HttpStatus.BAD_REQUEST);
                }
            }
            return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,UNAUTHORIZE,""),HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return  new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<BaseResponse> updatecategory(Map<String, String> requestMap) {


       try {


             if(jwtFilter.isAdmin()){

                if(validateCategoryMap(requestMap,true)){

                    Optional<Category> category= repo.findById(Integer.parseInt(requestMap.get("id")));
                    if(category.isPresent()){
                        repo.save(getCategoryFromMap(requestMap,true));
                        return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,UPDATE_MESSAGE,UPDATE_MESSAGE_BN),HttpStatus.OK);

                    }else {
                        return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,ID_DOESNOT_EXIST,ID_DOESNOT_EXIST_BN),HttpStatus.BAD_REQUEST);
                    }


                }else {
                    return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,INVALID_DATA,INVALID_DATA_BN),HttpStatus.BAD_REQUEST);

                }

            }
            else {
                return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,UNAUTHORIZE,""),HttpStatus.UNAUTHORIZED);
            }

       }catch (Exception e){
           return  new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.OK);
       }
    }


    private  Category getCategoryFromMap(Map<String,String>requestMap,Boolean isAdd){
        Category category=new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
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
