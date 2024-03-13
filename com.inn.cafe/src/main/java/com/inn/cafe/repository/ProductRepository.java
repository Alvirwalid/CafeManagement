package com.inn.cafe.repository;

import com.inn.cafe.POJO.Products;
import com.inn.cafe.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {

    List<ProductWrapper> getAllProduct();
    String updateQ ="update products p  set p.status=:status   where p.id=:id";
    @Transactional
    @Modifying
    @Query(value = updateQ,nativeQuery = true)
    void updateProductStatus(@Param("status") String status ,@Param("id") Integer id);

//    String q="select p.* from products where p.id=%:id%";
//    @Query(value = q,nativeQuery = true)
    ProductWrapper getProductById(@Param("id") Integer id);

    List<ProductWrapper> getProductByCategoryId(@Param("id") Integer id);

}
