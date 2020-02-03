package com.program.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.program.demo.controller.MessagesController;
import com.program.demo.model.Cars;
import com.program.demo.model.Favourites;
import com.program.demo.model.Messages;
import com.program.demo.model.Motobikes;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
import com.program.demo.repositories.CarsRepository;
import com.program.demo.repositories.FavouritesRepository;
import com.program.demo.repositories.MessagesRepository;
import com.program.demo.repositories.UserRepository;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MessagesControllerTest {
    @Autowired protected WebApplicationContext wac;
    @Autowired MessagesController messageController;
    @MockBean MessagesRepository messageRepository;
    @MockBean UserRepository userRepository;
    Messages message1;
    Messages message2;
    User testUser1;
    User testUser2;

    List<Messages> allMessages;
    private MockMvc mockMvc;


     /**
   * Setup method for the user controller test.
   */
  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();

    testUser1 = new User();
    testUser1.setId(1);
    testUser1.setUsername("testuser1");
    testUser1.setPassword("testpw");
    testUser1.setRole(User.Role.ROLE_ADMIN);

    testUser2 = new User();
    testUser2.setId(2);
    testUser2.setUsername("testuser2");
    testUser2.setPassword("testpw2");
    testUser2.setRole(User.Role.ROLE_COMPANY);

    message1 = new Messages();
    message1.setId(99);
    message1.setText("testmessage1");
    message1.setSender(1);
    message1.setReciver(2);

    message2 = new Messages();
    message2.setId(100);
    message2.setText("testmessage2");
    message2.setSender(2);
    message2.setReciver(1);
  }

    // ------MessagesController.getMessages() tests-----
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void getMessagesTest_multipleMessages() throws Exception {
      allMessages = new ArrayList<Messages>();
      allMessages.add(message1);
      allMessages.add(message2);
  
      when(messageRepository.findAll()).thenReturn(allMessages);
  
      mockMvc
          .perform(
              get("/messages")
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].text", is("testmessage1")))
          .andExpect(jsonPath("$[1].text", is("testmessage2")));
    }
  
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
      public void getMessagesTest_emptyMessages() throws Exception {
          allMessages = new ArrayList<Messages>();
  
          when(messageRepository.findAll()).thenReturn(allMessages);
  
          mockMvc
              .perform(get("/messages").contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(0)));
      }
  
      @Test
      @WithMockUser(
          username = "admin",
          roles = {"ADMIN"})
      public void getMessagesTest_singleMessages() throws Exception {
          allMessages = new ArrayList<Messages>();
          allMessages.add(message1);
  
          when(messageRepository.findAll()).thenReturn(allMessages);
  
          mockMvc
              .perform(get("/messages").contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(1)))
              .andExpect(jsonPath("$[0].text", is("testmessage1")));
      }
      // -----MessagesController.getMessages() tests-----
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void getMessagesTest_presentMessages() throws Exception {
  
      when(messageRepository.findById(message1.getId())).thenReturn(Optional.of(message1));
  
      mockMvc
          .perform(get("/messages/" + message1.getId()).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.text", is("testmessage1")));
    }
  
          
      @Test
      @WithMockUser(
          username = "admin",
          roles = {"ADMIN"})
      public void getMessagesTest_notPresentMessages() throws Exception {
        when(messageRepository.findById(69)).thenReturn(Optional.empty());
    
        mockMvc
            .perform(get("/messages/" + 69).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
      }
  
     
      
  
  }
  
  