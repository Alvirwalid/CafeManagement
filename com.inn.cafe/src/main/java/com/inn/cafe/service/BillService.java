package com.inn.cafe.service;

import com.inn.cafe.POJO.Bill;
import com.inn.cafe.constant.BaseConstant;
import com.inn.cafe.utils.BaseResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService extends BaseConstant {


    ResponseEntity<BaseResponse>generateReport(Map<String,String> requestMap);
    ResponseEntity<BaseResponse>getAllBills();
    ResponseEntity<BaseResponse>getPdf(Map<String,String>request);
    ResponseEntity<BaseResponse>deleteBill(Integer id);



}
