package com.program.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.program.demo.model.Messages;
import com.program.demo.model.User;
import com.program.demo.repositories.MessagesRepository;
import com.program.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/messages")
@CrossOrigin
@RestController
public class MessagesController{
    @Autowired private MessagesRepository messageRepository;
    @Autowired private UserRepository userRepository;

    public ResponseEntity<List<Messages>> getMessages() {
        List<Messages> messages = messageRepository.findAll();
        return ResponseEntity.ok(messages);
    }

    public ResponseEntity<Messages> getMessage(@PathVariable Integer id) {
        Optional<Messages> optionalUser = messageRepository.findById(id);
        if (optionalUser.isPresent()) {
          return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Messages> addMessage(
        @RequestBody Messages message, @PathParam(value = "author") Integer author) {
        Optional<User> optionalUser = userRepository.findById(author);
        if (optionalUser.isPresent()) {
        
        return ResponseEntity.ok(messageRepository.save(message));
        }
        return ResponseEntity.notFound().build();
    }

}