package com.program.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.program.demo.model.Cars;
import com.program.demo.model.Favourites;
import com.program.demo.model.Motobikes;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
import com.program.demo.repositories.CarsRepository;
import com.program.demo.repositories.FavouritesRepository;
import com.program.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/favourites")
@CrossOrigin
@RestController
public class FavouritesController{
    @Autowired private FavouritesRepository favouritesRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CarsRepository carRepository;
    @Autowired private BikesRepository bikeRepository;

    /**
     * Return all favourites.
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<List<Favourites>> getFavourites() {
        List<Favourites> favourites = favouritesRepository.findAll();
        return ResponseEntity.ok(favourites);
    }
    /**
     * Retruns the favourite that matches the given id.
     * @param id The id of the favourite.
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Favourites> getFavourite(@PathVariable Integer id) {
        Optional<Favourites> optionalUser = favouritesRepository.findById(id);
        if (optionalUser.isPresent()) {
          return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
    /**
     * Returns all the favourites that are the given users favourite.
     * @param user The id of the user.  
     * @return ResponsEntry
     */
    @GetMapping("/by-user")
    
    public ResponseEntity<List<Favourites>> getFavouriteByUser(@PathParam(value = "user") Integer user) {
    List<Favourites> favourites = favouritesRepository.findAllByUserId(user);
    return ResponseEntity.ok(favourites);
    }

    /**
     * Adds a new favourite to the user.
     * @param favourite The id of the favourite.
     * @param user The id of the user.
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Favourites> addFavourite(
        @RequestBody Favourites fav, @PathParam(value = "user_id") Integer user_id,
        @PathParam(value = "car_id") Integer car_id,
        @PathParam(value = "motobike_id") Integer motobike_id) {
        Optional<User> optionalUser = userRepository.findById(user_id);
        Optional<Cars> optionalCar = null;
        if(car_id != 0){
          optionalCar= carRepository.findById(car_id);
        }
        Optional<Motobikes> optionalBike = null;
        if(motobike_id != 0 ){
        
          optionalBike= bikeRepository.findById(motobike_id);
        }
        if (optionalUser.isPresent() && car_id !=0  &&
        motobike_id == 0) {
          fav.setUser(optionalUser.get());
          fav.setFavouriteCar(optionalCar.get());
          
          Favourites newFav = favouritesRepository.saveAndFlush(fav);
        return ResponseEntity.ok(newFav);
        }
        else if(optionalUser.isPresent() && motobike_id != 0 &&
        car_id == 0){
          fav.setUser(optionalUser.get());
          fav.setFavouriteBike(optionalBike.get());
          
          Favourites newFav = favouritesRepository.saveAndFlush(fav);
        return ResponseEntity.ok(newFav);
        }


        return ResponseEntity.notFound().build();
    }

}