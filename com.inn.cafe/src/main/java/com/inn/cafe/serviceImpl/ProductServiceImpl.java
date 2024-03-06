package com.inn.cafe.serviceImpl;

import com.inn.cafe.POJO.Category;
import com.inn.cafe.POJO.Products;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.repository.ProductRepository;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements com.inn.cafe.service.ProductService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ProductRepository repo;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {

        try {
            if(jwtFilter.isAdmin()){
           if(validateProductMap(requestMap,false)){

               System.out.println("validateProductMap");
                  repo.save(getProductFromMap(requestMap,false));
                 return  CafeUtils.getResponseEntity("Product add Successfully",HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeConstant.UNAUTHORIZE,HttpStatus.UNAUTHORIZED);


        }catch (Exception e){
            e.printStackTrace();
        }


        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Products>> getAllProduct() {
        return null;
    }
    private  boolean validateProductMap(Map<String,String>requestMap,boolean validateId){
        if(requestMap.containsKey("name")){



            if(requestMap.containsKey("id") && validateId){
                return  true;
            }else  if(!validateId){
                return  true;
            }



        }
        return  false;
    }

    private  Products getProductFromMap(Map<String,String>requestMap,boolean isAdd){
        Category category=new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Products products=new Products();
        if(isAdd){
            products.setId(Integer.parseInt(requestMap.get("id")));
        }else {
            products.setStatus("true");
        }

        products.setName(requestMap.get("name"));
        products.setCategory(category);
        products.setDescription(requestMap.get("description"));
        products.setPrice(Integer.parseInt(requestMap.get("price")));

        return  products;


    }
}
