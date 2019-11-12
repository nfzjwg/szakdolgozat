package com.program.demo.controller;

import com.program.demo.model.Cars;
import com.program.demo.model.Motobikes;
import com.program.demo.model.Receipt;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
import com.program.demo.repositories.CarsRepository;
import com.program.demo.repositories.ReceiptRepository;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/receipts")
@CrossOrigin
@RestController
public class ReceiptController{

    @Autowired private ReceiptRepository receitpRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CarsRepository carRepository;
    @Autowired private BikesRepository bikeRepository;
    /**
     * Returns all the receipts presented in the Receipt table.
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<List<Receipt>> getReceipts() {
        List<Receipt> receipt = receitpRepository.findAll();
        return ResponseEntity.ok(receipt);
    }
    /**
     * Returns a receipt that is matcing the given id.
     * @param id The id of the receipt.
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceipt(@PathVariable Integer id) {
        Optional<Receipt> optionalReceipt = receitpRepository.findById(id);
        if (optionalReceipt.isPresent()) {
          return ResponseEntity.ok(optionalReceipt.get());
        }
        return ResponseEntity.notFound().build();
    }
/**
 * Returns all the receipts that are the given users receipt.
 * @param owner The id of the user.  
 * @return ResponsEntry
 */
    @GetMapping("/by-user")
  
  public ResponseEntity<List<Receipt>> getReceiptsByUser(@PathParam(value = "user") Integer user) {
    List<Receipt> receipts = receitpRepository.findAllByUserId(user);
    return ResponseEntity.ok(receipts);
  }

    /**
     * Deletes the receipt with the given id.
     * @param id The id of the receipt.
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Receipt> deleteReceipt(@PathVariable Integer id) {
      Optional<Receipt> optionalReceipt = receitpRepository.findById(id);
      if (optionalReceipt.isPresent()) {
        receitpRepository.delete(optionalReceipt.get());
        return ResponseEntity.ok().build();
      }
      return ResponseEntity.notFound().build();
    }
    /**
     * Adds a receipt to the table.
     * @param receipt The new receipt.
     * @param user The id of the user.
     * @return
     */
    @PostMapping("/upload")
    //@Secured({"ROLE_ADMIN", "ROLE_COMPANY"})
    public ResponseEntity<Receipt> addReceipt(
        @RequestBody Receipt receipt, @PathParam(value = "user_id") Integer user_id,
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
            receipt.setUser(optionalUser.get());
            receipt.setCar(optionalCar.get());
          
          Receipt newReceipt = receitpRepository.saveAndFlush(receipt);
        return ResponseEntity.ok(newReceipt);
        }
        else if(optionalUser.isPresent() && motobike_id != 0 &&
        car_id == 0){
            receipt.setUser(optionalUser.get());
            receipt.setMotobike(optionalBike.get());
          
          Receipt newReceipt = receitpRepository.saveAndFlush(receipt);
        return ResponseEntity.ok(newReceipt);
        }
        return ResponseEntity.notFound().build();
    }
}
