package com.program.demo.controller;

import java.util.List;
import java.util.Optional;

import com.program.demo.model.User;
import com.program.demo.repositories.UserRepository;
import com.program.demo.security.AuthenticatedUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller class for the User entity.
 */
@RequestMapping("/users")
@CrossOrigin
@RestController
public class UserController {

  @Autowired private UserRepository userRepository;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  @Autowired private AuthenticatedUser authenticatedUser;

  /**
   * Returns all the registerd users.
   * @return ResponseEntity
   */
  @GetMapping("")
  //@Secured({"ROLE_ADMIN", "ROLE_GUEST"})
  public ResponseEntity<List<User>> getUsers() {
    List<User> users = userRepository.findAll();
    return ResponseEntity.ok(users);
  }

  /**
   * Returns user with the provided id if exists.
   * @param id The id of the user.
   * @return ResponseEntity
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable Integer id) {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      return ResponseEntity.ok(optionalUser.get());
    }
    return ResponseEntity.notFound().build();
  }
  /**
   * Registers the given user.
   * @param user The user that will be registerd.
   * @return ResponseEntity
   */
  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody User user) {
    Optional<User> optionalAppUser = userRepository.findByUsername(user.getUsername());
    if (optionalAppUser.isPresent()) {
      return ResponseEntity.badRequest().build();
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return ResponseEntity.ok(userRepository.save(user));
  }
  /**
   * Logs in the user.
   * @return ResponseEntity
   */
  @PostMapping("login")
  public ResponseEntity login() {
    return ResponseEntity.ok(authenticatedUser.getUser());
  }

/*
  @DeleteMapping("/{id}")
  public ResponseEntity deleteComment(@PathVariable Integer id) {
    Optional<User> optionalComment = userRepository.findById(id);
    if (optionalComment.isPresent()) {
      userRepository.delete(optionalComment.get());
      return ResponseEntity.ok().build();
    }
    
    return ResponseEntity.notFound().build();
  }
*/

  
}
