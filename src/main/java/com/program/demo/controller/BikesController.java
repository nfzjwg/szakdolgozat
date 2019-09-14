package com.program.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.program.demo.model.Bikes;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
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

@RequestMapping("/bikes")
@CrossOrigin
@RestController
public class BikesController{

    @Autowired private BikesRepository bikeRepository;
    @Autowired private UserRepository userRepository;
    @GetMapping("")
    public ResponseEntity<List<Bikes>> getBikes() {
        List<Bikes> cars = bikeRepository.findAll();
        return ResponseEntity.ok(cars);
    }

    public ResponseEntity<Bikes> getBike(@PathVariable Integer id) {
        Optional<Bikes> optionalUser = bikeRepository.findById(id);
        if (optionalUser.isPresent()) {
          return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Bikes> deleteBike(@PathVariable Integer id) {
      Optional<Bikes> optionalComment = bikeRepository.findById(id);
      if (optionalComment.isPresent()) {
        bikeRepository.delete(optionalComment.get());
        return ResponseEntity.ok().build();
      }
      return ResponseEntity.notFound().build();
    }
    @PostMapping("")

    public ResponseEntity<Bikes> addBike(
        @RequestBody Bikes bike, @PathParam(value = "author") Integer author) {
        Optional<User> optionalUser = userRepository.findById(author);
        if (optionalUser.isPresent()) {
        
        return ResponseEntity.ok(bikeRepository.save(bike));
        }
        return ResponseEntity.notFound().build();
    }

  
}
