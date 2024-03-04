package com.inn.cafe.repository;

import com.inn.cafe.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {


//    String getAllQ="select c.* from category c";
//    @Query(value = getAllQ,nativeQuery = true)
    List<Category>getAllCategory();
}
