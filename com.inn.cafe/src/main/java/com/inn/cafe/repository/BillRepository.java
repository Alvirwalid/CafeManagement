package com.inn.cafe.repository;

import com.inn.cafe.POJO.Bill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BillRepository extends JpaRepository<Bill,Integer> {

    String query="select b.* from bill b order by b.id desc";
    @Modifying
    @Transactional
    @Query(value = query,nativeQuery = true)
    List<Bill> getAllBills();
//
    String search="select b.* from bill b where b.created_by=%:username%  order by b.id desc";
    @Transactional
    @Modifying
    @Query(value = search,nativeQuery = true)
    List<Bill>getBillByUsername(@Param("username") String username);
}
