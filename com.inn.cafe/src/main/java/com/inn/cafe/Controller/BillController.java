package com.inn.cafe.Controller;


import com.google.common.io.BaseEncoding;
import com.inn.cafe.serviceImpl.BillServieImpl;
import com.inn.cafe.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillServieImpl servie;


    @PostMapping("/generateReport")
    public ResponseEntity<BaseResponse>generateReport(@RequestBody Map<String,String>requestMap){
        System.out.println("Generate Report : "+requestMap);
        return servie.generateReport(requestMap);
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse>getAllBills(){
        return  servie.getAllBills();
    }

    @PostMapping("/getPdf")
    public  ResponseEntity<BaseResponse>getPdf(@RequestBody Map<String,String>request){

        return servie.getPdf(request);
    }
    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<BaseResponse>delete(@PathVariable Integer id){
        return  servie.deleteBill(id);
    }


}
