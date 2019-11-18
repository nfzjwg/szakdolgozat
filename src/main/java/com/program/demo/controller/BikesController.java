package com.program.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.program.demo.model.Motobikes;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
import com.program.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/motobikes")
@CrossOrigin
@RestController
public class BikesController{

    @Autowired private BikesRepository bikeRepository;
    @Autowired private UserRepository userRepository;
    /**
     * This function returns all the motorcycles in the table.
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<List<Motobikes>> getBikes() {
        List<Motobikes> cars = bikeRepository.findAll();
        return ResponseEntity.ok(cars);
    }
    /**
     * Returns the specified motorcycle with the matching id.
     * @param id The id of the motorcycle.
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Motobikes> getBike(@PathVariable Integer id) {
        Optional<Motobikes> optionalUser = bikeRepository.findById(id);
        if (optionalUser.isPresent()) {
          return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
    /**
     * Deletes te motorcycle with the matching id.
     * @param id The id of the motorcycle.
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_COMPANY"})
    public ResponseEntity<Motobikes> deleteBike(@PathVariable Integer id) {
      Optional<Motobikes> optionalBike = bikeRepository.findById(id);
      if (optionalBike.isPresent()) {
        bikeRepository.delete(optionalBike.get());
        return ResponseEntity.ok().build();
      }
      return ResponseEntity.notFound().build();
    }

    /**
     * Deletes te motorcycle if it belongs to the given owner.
     * @param id The id of the owner.
     * @return ResponseEntity
     */
    @DeleteMapping("/by-owner/{id}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<Motobikes> deleteBikeByOwner(@PathVariable Integer id) {
      Optional<Motobikes> optionalBike = bikeRepository.findByOwnerId(id);
      if (optionalBike.isPresent()) {
        bikeRepository.delete(optionalBike.get());
        return ResponseEntity.ok().build();
      }
      return ResponseEntity.notFound().build();
    }

/**
 * Returns all the motobikes that are the given owner's motobikes.
 * @param owner The id of the owner.  
 * @return ResponsEntry
 */
@GetMapping("/by-user")
  
public ResponseEntity<List<Motobikes>> getCarsByUser(@PathParam(value = "owner") Integer owner) {
  List<Motobikes> cars = bikeRepository.findAllByOwnerId(owner);
  return ResponseEntity.ok(cars);
}


/**
 * Adds a motorcycle to the table.
 * @param bike This is the motorcycle.
 * @param owner The id of the owner.
 * @return ResponseEntity
 */
@PostMapping("/upload")
@Secured({"ROLE_ADMIN", "ROLE_COMPANY"})
public ResponseEntity<Motobikes> addBike(
    @RequestBody Motobikes bike, @PathParam(value = "owner") Integer owner) {
    Optional<User> optionalUser = userRepository.findById(owner);
    if (optionalUser.isPresent()) {
    bike.setOwner(optionalUser.get());
    Motobikes newBike = bikeRepository.saveAndFlush(bike);
    return ResponseEntity.ok(newBike);
    }
    return ResponseEntity.notFound().build();
}
/**
 * Updates a bike in the bike table.
 * @param bike The bike with the new data.
 * @param id  The id of the bike that will be updated.
 * @return ResponseEntity
 */
@PutMapping("/{id}")
@Secured({"ROLE_COMPANY"})
public ResponseEntity<Motobikes> putCar(@RequestBody Motobikes bike,
 @PathVariable Integer id) {
  Optional<Motobikes> optionalBike = bikeRepository.findById(id);
  System.out.println(optionalBike.toString());
  if (optionalBike.isPresent()) {
    System.out.println(optionalBike.toString());
    Motobikes oldBike = optionalBike.get();
    bike.setId(oldBike.getId());
    bike.setRented(oldBike.getRented());
    bike.setOwner(oldBike.getOwner());
    bike.RentList(oldBike.RentList());
    bike.FavouriteList(oldBike.FavouriteList());
    bike.ReceiptList(oldBike.ReceiptList());
    return ResponseEntity.ok(bikeRepository.save(bike));
  }
  return ResponseEntity.notFound().build();
}
  
}
