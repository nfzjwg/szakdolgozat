package com.program.demo.repositories;
import com.program.demo.model.Favourites;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Integer> {
    List<Favourites> findAllByUserId(Integer user_id);
    List<Favourites> findAllByCarId(Integer car_id);
    List<Favourites> findAll();
}
