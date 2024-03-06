package com.inn.cafe.serviceImpl;

import com.google.common.base.Strings;
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
import java.util.Objects;


@Service
public class CategoryServiceImpl implements CategoryService {

@Autowired
    CategoryRepository repo;
    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<List<Category>> getAllcategory(String filterData) {

        try{
            if(!Strings.isNullOrEmpty(filterData) && filterData.equalsIgnoreCase("true")){

//                System.out.println("True");

                return new  ResponseEntity<List<Category>>(repo.getAllCategory(),HttpStatus.OK);
            }


//            System.out.println("False");
            return new  ResponseEntity<List<Category>>(repo.findAll(),HttpStatus.OK);

        }catch (Exception e){e.printStackTrace();}

        return new  ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addCategory(Map<String,String>requestMap) {


        try {
            if(jwtFilter.isAdmin()){

                if(validateCategoryMap(requestMap,false)){
                    repo.save(getCategoryFromMap(requestMap,false));
                    return  CafeUtils.getResponseEntity("Category Added Successfully",HttpStatus.OK);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updatecategory(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){

                if(validateCategoryMap(requestMap,true)){

                    Category category= repo.getById(Integer.parseInt(requestMap.get("id")));
                    if(!Objects.isNull(category)){

                        repo.save(getCategoryFromMap(requestMap,true));


                        return  CafeUtils.getResponseEntity("Update SuccessFully",HttpStatus.OK);

                    }else {
                        return  CafeUtils.getResponseEntity("Category id does not exists",HttpStatus.BAD_REQUEST);
                    }


                };
                return  CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);

            }
            return  CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZE,HttpStatus.UNAUTHORIZED);
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
