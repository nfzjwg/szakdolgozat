package com.program.demo.repositories;
import java.util.List;
import java.util.Optional;

import com.program.demo.model.Cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CarsRepository extends JpaRepository<Cars, Integer> {
  Optional<Cars> findById(Integer car_id);
  List<Cars> findAllByOwnerId(Integer ownerId);
  List<Cars> findAll();
}
