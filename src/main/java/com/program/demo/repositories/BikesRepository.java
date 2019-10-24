package com.program.demo.repositories;
import com.program.demo.model.Motobikes;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BikesRepository extends JpaRepository<Motobikes, Integer> {

  Optional<Motobikes> findById(Integer bike_id);
  List<Motobikes> findAll();
  List<Motobikes> findAllByOwnerId(Integer ownerId);
}
