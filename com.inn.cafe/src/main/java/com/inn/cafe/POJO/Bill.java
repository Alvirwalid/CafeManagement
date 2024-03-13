package com.inn.cafe.POJO;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


@Data
@Entity
@DynamicUpdate
@DynamicInsert
public class Bill {

    private  static final   long serialVersionUID=1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private  Integer id;

    @Column( name = "uuid")
    private  String uuid;
    @Column(name = "name")
    private  String name;
    @Column( name = "email")
    private  String email;

    @Column( name = "contactNumber")
    private  String contactNumber;
    @Column( name = "paymentMethod")
    private  String paymentMethod;

    @Column(name = "totalAmount")
    private  Integer total;
    @Column( name = "productDetails",columnDefinition = "json")
    private  String productDetails;
    @Column(name = "createdBy")
    private  String createdBy;
}
