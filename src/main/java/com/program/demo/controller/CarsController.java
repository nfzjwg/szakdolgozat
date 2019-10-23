package com.program.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.program.demo.model.Cars;
import com.program.demo.model.User;
import com.program.demo.repositories.CarsRepository;
import com.program.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cars")
@CrossOrigin
@RestController
public class CarsController{

    @Autowired private CarsRepository carRepository;
    @Autowired private UserRepository userRepository;
    /**
     * Returns all the cars presented in the Cars table.
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<List<Cars>> getCars() {
        List<Cars> cars = carRepository.findAll();
        return ResponseEntity.ok(cars);
    }
    /**
     * Returns a car that is matcing the given id.
     * @param id The id of the car.
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cars> getCar(@PathVariable Integer id) {
        Optional<Cars> optionalUser = carRepository.findById(id);
        if (optionalUser.isPresent()) {
          return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
/**
 * Returns all the cars that are the given owners cars.
 * @param owner The id of the owner.  
 * @return ResponsEntry
 */
    @GetMapping("/by-user")
  //@Secured({"ROLE_AUTHOR", "ROLE_GUEST"})
  public ResponseEntity<List<Cars>> getCarsByUser(@PathParam(value = "owner") Integer owner) {
    List<Cars> cars = carRepository.findAllByOwnerId(owner);
    return ResponseEntity.ok(cars);
  }

    /**
     * Deletes the car with the given id.
     * @param id The id of the car.
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Cars> deleteCar(@PathVariable Integer id) {
      Optional<Cars> optionalComment = carRepository.findById(id);
      if (optionalComment.isPresent()) {
        carRepository.delete(optionalComment.get());
        return ResponseEntity.ok().build();
      }
      return ResponseEntity.notFound().build();
    }
    /**
     * Adds a car to the table.
     * @param car The car.
     * @param owner The id of the owner.
     * @return
     */
    @PostMapping("/upload")

    public ResponseEntity<Cars> addCar(
        @RequestBody Cars car, @PathParam(value = "owner") Integer owner) {
        Optional<User> optionalUser = userRepository.findById(owner);
        if (optionalUser.isPresent()) {
          System.out.println("USER FOUND");
          car.setOwner(optionalUser.get());
          System.out.println(car.getModel());
          System.out.println(car.getDoors());
          System.out.println(car.getEngine());
          System.out.println(car.getAc());
          System.out.println(car.getRented());
          Cars newCar = carRepository.saveAndFlush(car);
        return ResponseEntity.ok(newCar);
        }

        return ResponseEntity.notFound().build();
    }

  
}
