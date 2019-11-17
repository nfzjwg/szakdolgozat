package com.program.demo.repositories;
import com.program.demo.model.Rents;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RentsRepository extends JpaRepository<Rents, Integer> {

    List<Rents> findAllByUserId(Integer user_id);
    List<Rents> findAllByCarId(Integer car_id);
    List<Rents> findAll();
    Optional<Rents> findByUserId(Integer user_id);
}
