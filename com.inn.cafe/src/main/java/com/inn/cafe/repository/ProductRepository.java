package com.inn.cafe.repository;

import com.inn.cafe.POJO.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {
}
