package com.program.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.program.demo.controller.UserController;
import com.program.demo.model.User;
import com.program.demo.repositories.UserRepository;

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
public class UserControllerTest {
    @Autowired protected WebApplicationContext wac;
    @Autowired UserController userController;
    @MockBean UserRepository userRepository;
    User requester;
    User user1;
    User user2;
    List<User> allUsers;
    private MockMvc mockMvc;


     /**
   * Setup method for the user controller test.
   */
  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();

    requester = new User();
    requester.setId(99);
    requester.setUsername("testuser1");
    requester.setPassword("testpw");
    requester.setRole(User.Role.ROLE_ADMIN);

    user1 = new User();
    user1.setId(100);
    user1.setUsername("testuser1");
    user1.setPassword("testpw");
    user1.setRole(User.Role.ROLE_GUEST);
    user2 = new User();
    user2.setId(101);
    user2.setUsername("testuser2");
    user2.setPassword("testpw");
    user2.setRole(User.Role.ROLE_GUEST);
  }

   // ------UserController.getUsers() tests-----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getUsersTest_multipleUsers() throws Exception {
    allUsers = new ArrayList<User>();
    allUsers.add(user1);
    allUsers.add(user2);

    when(userRepository.findAll()).thenReturn(allUsers);

    mockMvc
        .perform(
            get("/users")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].username", is("testuser1")))
        .andExpect(jsonPath("$[1].username", is("testuser2")));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
    public void getUsersTest_emptyUsers() throws Exception {
        allUsers = new ArrayList<User>();

        when(userRepository.findAll()).thenReturn(allUsers);

        mockMvc
            .perform(get("/users").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void getUsersTest_singleUser() throws Exception {
        allUsers = new ArrayList<User>();
        allUsers.add(user1);

        when(userRepository.findAll()).thenReturn(allUsers);

        mockMvc
            .perform(get("/users").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].username", is("testuser1")));
    }
    // -----UserController.getUser() tests-----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getUserTest_presentUser() throws Exception {

    when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

    mockMvc
        .perform(get("/users/" + user1.getId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username", is("testuser1")));
  }

        
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void getUserTest_notPresentUser() throws Exception {
      when(userRepository.findById(69)).thenReturn(Optional.empty());
  
      mockMvc
          .perform(get("/users/" + 69).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    }

     // -----putUser() tests-----
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void putUserTest_ExistingUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user1);

        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        mockMvc
            .perform(
                put("/users/" + user1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void putUserTest_NotExistingUser() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user1);

        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());

        mockMvc
            .perform(
                put("/users/" + user1.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
            .andExpect(status().isNotFound());
    }

    // ----- deleteUser() test -----
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void deleteUserTest_PresentUser() throws Exception {

        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        mockMvc
            .perform(delete("/users/" + user1.getId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void deleteUserTest_NotPresentUser() throws Exception {
        when(userRepository.findById(user1.getId())).thenReturn(Optional.empty());

        mockMvc
            .perform(delete("/users/" + user1.getId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

  

}