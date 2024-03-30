package com.inn.cafe.service;

import com.inn.cafe.utils.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DashboardService {

    ResponseEntity<BaseResponse>getCount();
}
