package com.inn.cafe.POJO;


import com.inn.cafe.POJO.auth.Token;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;


//@NamedQuery(name = "Category.getAllCategory",query = "select c from  Category c")
//@NamedQuery(name = "Category.getById",query = "select c from Category c where  c.id=:id")
@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "category")
public class Category implements Serializable {
    private  static  final  long serialVersionUID=1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private   Integer id;
    @Column(name = "name")
    private  String name;

//    @OneToMany(mappedBy = "category")
//    private List<Products> products;
}
