package com.inn.cafe.service;
import com.inn.cafe.constant.BaseConstant;
import com.inn.cafe.utils.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

public interface CategoryService extends BaseConstant {

    ResponseEntity<BaseResponse>getAllcategory(String filterData);

    ResponseEntity<BaseResponse>addCategory(Map<String,String> requestMap);


    ResponseEntity<BaseResponse>updatecategory(@RequestBody Map<String,String>requestMap);
    ResponseEntity<BaseResponse>deleteCategory(Integer id);
}
