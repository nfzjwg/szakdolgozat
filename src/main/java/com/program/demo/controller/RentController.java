package com.program.demo.controller;

import com.program.demo.model.Cars;
import com.program.demo.model.Motobikes;
import com.program.demo.model.Rents;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
import com.program.demo.repositories.CarsRepository;
import com.program.demo.repositories.RentsRepository;
import com.program.demo.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rents")
@CrossOrigin
@RestController
public class RentController{

    @Autowired private RentsRepository rentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CarsRepository carRepository;
    @Autowired private BikesRepository bikeRepository;
    /**
     * Returns all the rents presented in the Rents table.
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<List<Rents>> getRents() {
        List<Rents> rents = rentRepository.findAll();
        return ResponseEntity.ok(rents);
    }
    /**
     * Returns a rent that is matcing the given id.
     * @param id The id of the rent.
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rents> getRent(@PathVariable Integer id) {
        Optional<Rents> optionalUser = rentRepository.findById(id);
        if (optionalUser.isPresent()) {
          return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
/**
 * Returns all the rents that are the given owners rents.
 * @param owner The id of the owner.  
 * @return ResponsEntry
 */
    @GetMapping("/by-user")
  
  public ResponseEntity<List<Rents>> getRentsByUser(@PathParam(value = "owner") Integer owner) {
    List<Rents> rents = rentRepository.findAllByUserId(owner);
    return ResponseEntity.ok(rents);
  }

    /**
     * Deletes the rent with the given id.
     * @param id The id of the rent.
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Rents> deleteRent(@PathVariable Integer id) {
      Optional<Rents> optionalRent = rentRepository.findById(id);
      if (optionalRent.isPresent()) {
        rentRepository.delete(optionalRent.get());
        return ResponseEntity.ok().build();
      }
      return ResponseEntity.notFound().build();
    }
    /**
     * Adds a rent to the table.
     * @param rent The new rent.
     * @param owner The id of the owner.
     * @return
     */
    @PostMapping("/upload")
    //@Secured({"ROLE_ADMIN", "ROLE_COMPANY"})
    public ResponseEntity<Rents> addRent(
        @RequestBody Rents rent, @PathParam(value = "user_id") Integer user_id,
        @PathParam(value = "car_id") Integer car_id,
        @PathParam(value = "motobike_id") Integer motobike_id) {
        Optional<User> optionalUser = userRepository.findById(user_id);
        Optional<Cars> optionalCar = null;
        if(car_id != 0){
          Cars helper = carRepository.findById(car_id).get();
          helper.setRented(true);
          optionalCar= carRepository.findById(car_id);
        }
        Optional<Motobikes> optionalBike = null;
        if(motobike_id != 0 ){
          Motobikes helper = bikeRepository.findById(motobike_id).get();
          helper.setRented(true);
          optionalBike= bikeRepository.findById(motobike_id);
        }
        if (optionalUser.isPresent() && car_id !=0  &&
        motobike_id == 0) {
          rent.setUser(optionalUser.get());
          rent.setCar(optionalCar.get());
          
          Rents newRent = rentRepository.saveAndFlush(rent);
        return ResponseEntity.ok(newRent);
        }
        else if(optionalUser.isPresent() && motobike_id != 0 &&
        car_id == 0){
          rent.setUser(optionalUser.get());
          rent.setMotobike(optionalBike.get());
          
          Rents newRent = rentRepository.saveAndFlush(rent);
        return ResponseEntity.ok(newRent);
        }


        return ResponseEntity.notFound().build();
    }

  /**
   * Updates the rent with the provided id if it exists.
   * @param rent The new rent data.
   * @param id The id of the rent.
   * @return ResponseEntity
   */
  @PutMapping("/{id}")
  public ResponseEntity<Rents> returnRentedItem(@RequestBody Rents rent, @PathVariable Integer id) {
    Optional<Rents> optionalRent = rentRepository.findById(id);
    if (optionalRent.isPresent()) {
      Rents oldRent = optionalRent.get();
      rent.setId(oldRent.getId());
      rent.setStart(oldRent.getStart());
      rent.setUser(oldRent.getUser());
      rent.setCar(oldRent.getCar());
      rent.setMotobike(oldRent.getMotobike());
      if(rent.getCar() != null){
        rent.getCar().setRented(!rent.getCar().getRented());
      }
      if(rent.getMotobike() != null){
        rent.getMotobike().setRented(!rent.getMotobike().getRented());
      }
      return ResponseEntity.ok(rentRepository.save(rent));
    }
    return ResponseEntity.notFound().build();
  }

}
