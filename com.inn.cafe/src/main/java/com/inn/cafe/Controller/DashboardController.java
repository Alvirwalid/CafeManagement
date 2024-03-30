package com.inn.cafe.Controller;


import com.inn.cafe.serviceImpl.DashboardServiceImpl;
import com.inn.cafe.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardServiceImpl service;


    @GetMapping("/details")
    public ResponseEntity<BaseResponse>getCount(){


        System.out.println("GetCount");

        return  service.getCount();
    }
}
