package com.inn.cafe.serviceImpl;

import com.inn.cafe.POJO.Category;
import com.inn.cafe.POJO.Products;
import com.inn.cafe.constant.CafeConstant;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.repository.ProductRepository;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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
           if(validatedProductMap(requestMap,false)){

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
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
            return  new ResponseEntity<List<ProductWrapper>>(repo.getAllProduct(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<ProductWrapper>>(new ArrayList<>(null),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            ProductWrapper products = repo.getProductById(id);
            if(!Objects.isNull(products)){
                return new ResponseEntity<ProductWrapper>(products,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {

        try {
            if(jwtFilter.isAdmin()){
                Products optional= repo.findById(Integer.parseInt(requestMap.get("id"))).orElseThrow(null);
                if(!Objects.isNull(optional)){
                    if(validatedProductMap(requestMap,true)){
                        Products products =getProductFromMap(requestMap,true);
                        products.setStatus(optional.getStatus());
                        repo.save(products);
                        return new ResponseEntity<>("Update Successfully",HttpStatus.OK);
                    }
                }else {
                    return new ResponseEntity<>("Id does not exist",HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(CafeConstant.UNAUTHORIZE,HttpStatus.UNAUTHORIZED);


        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> delete(Integer id) {

        try{

            if(jwtFilter.isAdmin()){

                Products products=repo.findById(id).orElseThrow();
                if(!Objects.isNull(products)){

                   repo.deleteById(products.getId());
                    return new ResponseEntity<>("Delete successfully",HttpStatus.OK);
                }
                    return new ResponseEntity<>("ID does not exist",HttpStatus.BAD_REQUEST);

            }
            return new ResponseEntity<>(CafeConstant.UNAUTHORIZE,HttpStatus.UNAUTHORIZED);

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {

        try{

            if(jwtFilter.isAdmin()){

                Products products=repo.findById(Integer.parseInt(requestMap.get("id"))).orElseThrow();


                if(!Objects.isNull(products)){


                   repo.updateProductStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));


                    return new ResponseEntity<>("Status update successfully",HttpStatus.OK);

                }
                return new ResponseEntity<>("ID does not exist",HttpStatus.BAD_REQUEST);

            }
            return new ResponseEntity<>(CafeConstant.UNAUTHORIZE,HttpStatus.UNAUTHORIZED);

        }catch (Exception e){
            e.printStackTrace();

        }

        return new ResponseEntity<>(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private  boolean validatedProductMap(Map<String,String>requestMap, boolean validateId){


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
