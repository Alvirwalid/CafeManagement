package com.inn.cafe.utils;

import lombok.Data;

import java.io.Serializable;
@Data
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean status;
    private String message;
    private String messageBn;
    private Object data;
}
