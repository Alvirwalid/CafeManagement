package com.inn.cafe.request;

import com.inn.cafe.POJO.auth.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.plaf.PanelUI;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    public  String name;
    public  String contactNumber;
    public  String username;
    public  String password;
    public  String status;
    public Role role;

}

