package com.inn.cafe.wrapper;

import com.inn.cafe.POJO.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private  Integer id;
    private String name;
    private String contactNumber;
    private String username;
    private String status;

    public UserWrapper(Integer id, String name, String contactNumber, String username, String status) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.username = username;
        this.status = status;
    }


}
