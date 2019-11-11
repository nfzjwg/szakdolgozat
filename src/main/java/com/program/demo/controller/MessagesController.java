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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/messages")
@CrossOrigin
@RestController
public class MessagesController{
    @Autowired private MessagesRepository messageRepository;
    @Autowired private UserRepository userRepository;

    /**
     * Return all messages.
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<List<Messages>> getMessages() {
        List<Messages> messages = messageRepository.findAll();
        return ResponseEntity.ok(messages);
    }
    /**
     * Retruns the message that matches the given id.
     * @param id The id of the message.
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Messages> getMessage(@PathVariable Integer id) {
        Optional<Messages> optionalUser = messageRepository.findById(id);
        if (optionalUser.isPresent()) {
          return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }
    /**
     * Returns all the messages that are the given users messages.
     * @param sender The id of the sender.  
     * @return ResponsEntry
     */
    @GetMapping("/by-user")
    
    public ResponseEntity<List<Messages>> getMessagesBySender(@PathParam(value = "sender") Integer sender) {
    List<Messages> message = messageRepository.findAllBySender(sender);
    return ResponseEntity.ok(message);
    }

    /**
     * Returns all the messages where the reciver id is the given id.
     * @param reciver The id of the reciver.  
     * @return ResponsEntry
     */
    @GetMapping("/by-reciver")
    
    public ResponseEntity<List<Messages>> getMessagesByReciver(@PathParam(value = "reciver") Integer reciver) {
    List<Messages> message = messageRepository.findAllByReciver(reciver);
    return ResponseEntity.ok(message);
    }

    /**
     * Adds a message to the user.
     * @param message The message.
     * @param author The id of the user.
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Messages> addMessage(
        @RequestBody Messages message, 
        @PathParam(value = "sender") Integer sender,
        @PathParam(value = "reciver") Integer reciver) {
        Optional<User> optionalSender = userRepository.findById(sender);
        Optional<User> optionalReciver = userRepository.findById(reciver);
        if (optionalSender.isPresent() && optionalReciver.isPresent()) {
            message.setSender(sender);
            message.setReciver(reciver);
            Messages newMessage = messageRepository.saveAndFlush(message);
        return ResponseEntity.ok(newMessage);
        }

        return ResponseEntity.notFound().build();
    }

}