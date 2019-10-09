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
     * Adds a car to the user given with the id.
     * @param car The car.
     * @param author The id of the user.
     * @return
     */
    @PostMapping("")

    public ResponseEntity<Cars> addCar(
        @RequestBody Cars car, @PathParam(value = "author") Integer author) {
        Optional<User> optionalUser = userRepository.findById(author);
        if (optionalUser.isPresent()) {
        
        return ResponseEntity.ok(carRepository.save(car));
        }

        return ResponseEntity.notFound().build();
    }

  
}
