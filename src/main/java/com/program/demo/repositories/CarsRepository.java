package com.program.demo.repositories;
import java.util.List;

import com.program.demo.model.Cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CarsRepository extends JpaRepository<Cars, Integer> {

  List<Cars> findAllByOwnerId(Integer ownerId);
  List<Cars> findAll();
}
