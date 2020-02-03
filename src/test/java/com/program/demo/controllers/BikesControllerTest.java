package com.program.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.program.demo.model.Motobikes;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BikesControllerTest {
    User testUser;
    Motobikes bike1;
    Motobikes bike2;
    List<Motobikes> allMotobikes;
    @Autowired private MockMvc mockMvc;
    @MockBean private BikesRepository bikeRepository;
    @MockBean private UserRepository userController;

    /**
   * Setup method for the bike controller test.
   */
  @Before
  public void setUp() {

    testUser = new User();
    testUser.setId(100);
    testUser.setUsername("testuser1");
    testUser.setPassword("testpw");
    testUser.setRole(User.Role.ROLE_ADMIN);

    bike1 = new Motobikes();
    bike1.setId(1);
    bike1.setManufacturer("testManufacturer1");
    bike1.setModel("testModell1");
    bike1.setCcm(600);
    bike1.setOwner(testUser);
    bike1.setRented(false);

    bike2 = new Motobikes();
    bike2.setId(2);
    bike2.setManufacturer("testManufacturer2");
    bike2.setModel("testModell2");
    bike2.setCcm(500);
    bike2.setOwner(testUser);
    bike2.setRented(false);
  }

   // ----- GetMotobikes() tests -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getMotobikesTest_multipleMotobikes() throws Exception {
     ArrayList<Motobikes> allMotobikes = new ArrayList<Motobikes>();
     allMotobikes.add(bike1);
     allMotobikes.add(bike2);
     when(bikeRepository.findAll()).thenReturn(allMotobikes);
     mockMvc
         .perform(get("/motobikes").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[0].ccm", is(600)))
         .andExpect(jsonPath("$[1].ccm", is(500)));
   }

   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getMotobikesTest_emptyMotobikes() throws Exception {
     ArrayList<Motobikes> allMotobikes = new ArrayList<Motobikes>();
     when(bikeRepository.findAll()).thenReturn(allMotobikes);
 
     mockMvc
         .perform(get("/motobikes").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(0)));
   }
 
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getMotobikesTest_singleMotobike() throws Exception {
     ArrayList<Motobikes> allMotobikes = new ArrayList<Motobikes>();
     allMotobikes.add(bike1);
 
     when(bikeRepository.findAll()).thenReturn(allMotobikes);
 
     mockMvc
         .perform(get("/motobikes").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].manufacturer", is("testManufacturer1")));
   }
 

   // ----- getMotobike() tests -----
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void getMotobiketTest_presentMotobike() throws Exception {
        when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.of(bike1));

        mockMvc
            .perform(get("/motobikes/" + bike1.getId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void getMotobikeTest_notPresentMotobike() throws Exception {
      when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.empty());
  
      mockMvc
          .perform(get("/motobikes/" + 1).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    }
    
     // -----putMotobike() tests-----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"COMPANY"})
  public void putMotobikeTest_NotExistingMotobike() throws Exception {
    // parsing into json for the request body
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(bike1);

    when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.empty());

    mockMvc
        .perform(
            put("/motobikes/" + bike1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"COMPANY"})
  public void putMotobikeTest_ExistingMotobike() throws Exception {
    // parsing into json for the request body
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(bike1);

    when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.of(bike1));

    mockMvc
        .perform(
            put("/motobikes/" + bike1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }

   // ----- deleteMotobike() test -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void deleteMotobikeTest_PresentMotobike() throws Exception {
 
     when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.of(bike1));
 
     mockMvc
         .perform(delete("/motobikes/" + bike1.getId()).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());
   }
 
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void deleteMotobikeTest_NotPresentMotobike() throws Exception {
 
     when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.empty());
 
     mockMvc
         .perform(delete("/motobikes/" + 69).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isNotFound());
   }
 
}