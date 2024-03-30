package com.inn.cafe.serviceImpl;


import com.inn.cafe.repository.BillRepository;
import com.inn.cafe.repository.CategoryRepository;
import com.inn.cafe.repository.ProductRepository;
import com.inn.cafe.service.DashboardService;
import com.inn.cafe.utils.BaseResponse;
import com.inn.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    BillRepository billRepository;

    @Autowired
    CafeUtils cafeUtils;
    @Override
    public ResponseEntity<BaseResponse> getCount() {
        try {

            Map<String,Object>map=new HashMap<>();

            map.put("category",categoryRepository.count());
            map.put("product",productRepository.count());
            map.put("bill",billRepository.count());

            System.out.println(map);

            BaseResponse res=new BaseResponse();

            res.setData(map);

            BaseResponse response =cafeUtils.generateSuccessResponse(map,"","");
            System.out.println(res);


            return new  ResponseEntity(cafeUtils.generateSuccessResponse(map,"",""), HttpStatus.OK);

        }catch (Exception e){

            return new ResponseEntity<>(cafeUtils.generateErrorResponse(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
