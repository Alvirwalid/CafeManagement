package com.inn.cafe.serviceImpl;

import com.inn.cafe.POJO.Category;
import com.inn.cafe.POJO.Products;
import com.inn.cafe.jwt.filter.JwtFilter;
import com.inn.cafe.repository.CategoryRepository;
import com.inn.cafe.repository.ProductRepository;
import com.inn.cafe.utils.BaseResponse;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.wrapper.ProductWrapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class ProductServiceImpl implements com.inn.cafe.service.ProductService {
    private  final CafeUtils cafeUtils;


    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ProductRepository repo;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public ResponseEntity<BaseResponse> addNewProduct(Map<String, String> requestMap) {

        try {
            if(jwtFilter.isAdmin()){
           if(validatedProductMap(requestMap,false)){

//               System.out.println("validateProductMap");
                  repo.save(getProductFromMap(requestMap,false));
                 return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, SAVE_MESSAGE,SAVE_MESSAGE_BN),HttpStatus.OK);
                }
                return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,INVALID_DATA,INVALID_DATA_BN),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,UNAUTHORIZE,""),HttpStatus.UNAUTHORIZED);


        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @Override
    public ResponseEntity<BaseResponse> getAllProduct() {


        try {
            return  new ResponseEntity<>(cafeUtils.generateSuccessResponse(repo.getAllProduct(),"",""),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> getProductById(Integer id) {
        try {
            Optional<ProductWrapper> products = Optional.ofNullable(repo.getProductById(id));
            if(products.isPresent()){
                System.out.println(" Present");

                return new ResponseEntity<>(cafeUtils.generateSuccessResponse(products.get(),"",""),HttpStatus.OK);
            }else {

                System.out.println("Not Present");
                return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,"ID does not exist","আইডি খুজে পাওয়া যাচ্ছে না"),HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<BaseResponse> update(Map<String, String> requestMap) {

        try {
            if(jwtFilter.isAdmin()){
               Optional <Products> optional= repo.findById(Integer.parseInt(requestMap.get("id")));
                if(optional.isPresent()){

                    if(validatedProductMap(requestMap,true)){
                        Products products =getProductFromMap(requestMap,true);
                        products.setStatus(optional.get().getStatus());
                        repo.save(products);
                        return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,UPDATE_MESSAGE,UPDATE_MESSAGE_BN),HttpStatus.OK);

                    }else {

                        System.out.println("inValid");
                        return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,INVALID_DATA,INVALID_DATA_BN),HttpStatus.BAD_REQUEST);
                    }
                }else {
                    return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,ID_DOESNOT_EXIST,ID_DOESNOT_EXIST_BN),HttpStatus.BAD_REQUEST);
                }

            }

            return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, UNAUTHORIZE,""),HttpStatus.UNAUTHORIZED);


        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<BaseResponse> delete(Integer id) {

        try{

            if(jwtFilter.isAdmin()){

                Optional<Products> products=repo.findById(id);
                if(products.isPresent()){
                   repo.deleteById(products.get().getId());
                    return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, DELETE_MESSAGE ,DELETE_MESSAGE_BN),HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,ID_DOESNOT_EXIST,ID_DOESNOT_EXIST_BN),HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,UNAUTHORIZE,""),HttpStatus.UNAUTHORIZED);

        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<BaseResponse> updateStatus(Map<String, String> requestMap) {
//

        try{
            if(jwtFilter.isAdmin()){

                Optional<Products> products=repo.findById(Integer.parseInt(requestMap.get("id")));
                if(products.isPresent()){
                   repo.updateProductStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
                    return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null, UPDATE_MESSAGE,UPDATE_MESSAGE_BN),HttpStatus.OK);
                }else {
                    return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,ID_DOESNOT_EXIST,ID_DOESNOT_EXIST_BN),HttpStatus.BAD_REQUEST);
                }

            }

            return new ResponseEntity<>(cafeUtils.generateSuccessResponse(null,UNAUTHORIZE,""),HttpStatus.UNAUTHORIZED);

        }catch (Exception e){
            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public ResponseEntity<BaseResponse> getProductByCategoryId(Integer id) {
        try {
            return new ResponseEntity<>( cafeUtils.generateSuccessResponse(repo.getProductByCategoryId(id),"",""),HttpStatus.OK) ;
        }catch (Exception e){
            return new ResponseEntity<>( cafeUtils.generateErrorResponse(e),HttpStatus.OK) ;
        }
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

//        System.out.println("Get Product Map");
        Category category=new Category();

        Optional <Category>  c= Optional.ofNullable(categoryRepository.getProducById(Integer.parseInt(requestMap.get("categoryId"))));
        category.setId(c.get().getId());
        category.setName(c.get().getName());

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
