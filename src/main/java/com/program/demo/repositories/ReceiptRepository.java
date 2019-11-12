package com.program.demo.repositories;
import java.util.List;

import com.program.demo.model.Receipt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {

    List<Receipt> findAllByUserId(Integer user_id);
    List<Receipt> findAllByCarId(Integer car_id);
    List<Receipt> findAll();
}