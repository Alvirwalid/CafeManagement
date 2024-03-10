package com.inn.cafe.repository;

import com.inn.cafe.POJO.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {


    String getAlLCat="select c.* from category c where c.id in (select p.category_fk from products p where p.status='true')";
    @Query(value = getAlLCat,nativeQuery = true)
    List<Category>getAllCategory();

    String  getIdQ="select c.* from category c where c.id=%:id%";
    @Query(name = getIdQ,nativeQuery = true)
    Category getProducById(@Param("id") Integer id);
}
