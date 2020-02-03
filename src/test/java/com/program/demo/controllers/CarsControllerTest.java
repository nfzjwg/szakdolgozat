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
import com.program.demo.model.Cars;
import com.program.demo.model.User;
import com.program.demo.repositories.CarsRepository;
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
public class CarsControllerTest {
    User testUser;
    Cars car1;
    Cars car2;
    List<Cars> allCars;
    @Autowired private MockMvc mockMvc;
    @MockBean private CarsRepository carRepository;
    @MockBean private UserRepository userController;

    /**
   * Setup method for the car controller test.
   */
  @Before
  public void setUp() {

    testUser = new User();
    testUser.setId(100);
    testUser.setUsername("testuser1");
    testUser.setPassword("testpw");
    testUser.setRole(User.Role.ROLE_ADMIN);

    car1 = new Cars();
    car1.setId(1);
    car1.setManufacturer("testManufacturer1");
    car1.setModel("testModell1");
    car1.setDoors(5);
    car1.setEngine("testBenzin");
    car1.setCcm(1600);
    car1.setAc(true);
    car1.setOwner(testUser);
    car1.setRented(false);

    car2 = new Cars();
    car2.setId(2);
    car2.setManufacturer("testManufacturer2");
    car2.setModel("testModell2");
    car2.setDoors(5);
    car2.setEngine("testDisel");
    car2.setCcm(2000);
    car2.setAc(true);
    car2.setOwner(testUser);
    car2.setRented(false);
  }

   // ----- GetCars() tests -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getCarsTest_multipleCars() throws Exception {
     ArrayList<Cars> allCars = new ArrayList<Cars>();
     allCars.add(car1);
     allCars.add(car2);
     when(carRepository.findAll()).thenReturn(allCars);
     mockMvc
         .perform(get("/cars").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[0].ccm", is(1600)))
         .andExpect(jsonPath("$[1].ccm", is(2000)));
   }

   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getCarsTest_emptyUsers() throws Exception {
     ArrayList<Cars> allCars = new ArrayList<Cars>();
     when(carRepository.findAll()).thenReturn(allCars);
 
     mockMvc
         .perform(get("/cars").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(0)));
   }
 
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getCarsTest_singleCar() throws Exception {
     ArrayList<Cars> allCars = new ArrayList<Cars>();
     allCars.add(car1);
 
     when(carRepository.findAll()).thenReturn(allCars);
 
     mockMvc
         .perform(get("/cars").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(1)))
         .andExpect(jsonPath("$[0].manufacturer", is("testManufacturer1")));
   }
 

   // ----- getCar() tests -----
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void getCartTest_presentCar() throws Exception {
        when(carRepository.findById(car1.getId())).thenReturn(Optional.of(car1));

        mockMvc
            .perform(get("/cars/" + car1.getId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(
        username = "admin",
        roles = {"ADMIN"})
    public void getCarTest_notPresentCar() throws Exception {
      when(carRepository.findById(car1.getId())).thenReturn(Optional.empty());
  
      mockMvc
          .perform(get("/cars/" + 1).contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    }
    
     // -----putCar() tests-----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"COMPANY"})
  public void putCarTest_NotExistingCar() throws Exception {
    // parsing into json for the request body
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(car1);

    when(carRepository.findById(car1.getId())).thenReturn(Optional.empty());

    mockMvc
        .perform(
            put("/cars/" + car1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"COMPANY"})
  public void putCarTest_ExistingCar() throws Exception {
    // parsing into json for the request body
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(car1);

    when(carRepository.findById(car1.getId())).thenReturn(Optional.of(car1));

    mockMvc
        .perform(
            put("/cars/" + car1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }

   // ----- deleteCar() test -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void deleteCarTest_PresentCar() throws Exception {
 
     when(carRepository.findById(car1.getId())).thenReturn(Optional.of(car1));
 
     mockMvc
         .perform(delete("/cars/" + car1.getId()).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());
   }
 
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void deleteCarTest_NotPresentCar() throws Exception {
 
     when(carRepository.findById(car1.getId())).thenReturn(Optional.empty());
 
     mockMvc
         .perform(delete("/cars/" + 69).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isNotFound());
   }
 
}