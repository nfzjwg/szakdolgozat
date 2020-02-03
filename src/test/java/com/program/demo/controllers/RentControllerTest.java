package com.program.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.program.demo.model.Cars;
import com.program.demo.model.Motobikes;
import com.program.demo.model.Rents;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
import com.program.demo.repositories.CarsRepository;
import com.program.demo.repositories.RentsRepository;
import com.program.demo.repositories.UserRepository;

import java.sql.Date;
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
public class RentControllerTest {
    User testUser;
    Motobikes bike1;
    Cars car1;
    Rents rent1;
    Rents rent2;
    List<Rents> allRents;
    @Autowired private MockMvc mockMvc;
    @MockBean private RentsRepository rentRepository;
    @MockBean private UserRepository userRepository;
    @MockBean private BikesRepository bikeRepository;
    @MockBean private CarsRepository carRepository;

    /**
    * Setup method for the bike controller test.
   */
    @Before
    public void setUp() {

        testUser = new User();
        testUser.setId(100);
        testUser.setUsername("testuser1");
        testUser.setPassword("testpw");
        testUser.setRole(User.Role.ROLE_GUEST);

        bike1 = new Motobikes();
        bike1.setId(1);
        bike1.setManufacturer("testManufacturer1");
        bike1.setModel("testModell1");
        bike1.setCcm(600);
        bike1.setOwner(testUser);
        bike1.setRented(false);

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

        rent1 = new Rents();
        rent1.setId(1);
        rent1.setStart(Date.valueOf("2019-12-01"));
        rent1.setEnd(Date.valueOf("2019-12-04"));
        rent1.setMotobike(bike1);
        rent1.setUser(testUser);
        rent1.setPayed(true);

        rent2 = new Rents();
        rent2.setId(2);
        rent2.setStart(Date.valueOf("2019-12-02"));
        rent2.setEnd(Date.valueOf("2019-12-04"));
        rent2.setMotobike(bike1);
        rent2.setUser(testUser);
        rent2.setPayed(true);
    }

     // ----- GetMotobikes() tests -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getRentsTest_multipleRents() throws Exception {
     ArrayList<Rents> allRents = new ArrayList<Rents>();
     allRents.add(rent1);
     allRents.add(rent2);
     when(rentRepository.findAll()).thenReturn(allRents);
     mockMvc
         .perform(get("/rents").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[1].id", is(2)));
   }
   @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getRentsTest_emptyRents() throws Exception {
    ArrayList<Rents> allRents = new ArrayList<Rents>();

    when(rentRepository.findAll()).thenReturn(allRents);

    mockMvc
        .perform(get("/rents").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getRentsTest_singleRent() throws Exception {
    ArrayList<Rents> allRents = new ArrayList<Rents>();
    allRents.add(rent1);

    when(rentRepository.findAll()).thenReturn(allRents);

    mockMvc
        .perform(get("/rents").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)));
  }

  // ----- getRent() tests -----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getRentTest_presentRent() throws Exception {
    when(rentRepository.findById(rent1.getId())).thenReturn(Optional.of(rent1));

    mockMvc
        .perform(get("/rents/" + rent1.getId()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getRentTest_notPresentRent() throws Exception {
    when(rentRepository.findById(rent1.getId())).thenReturn(Optional.empty());

    mockMvc
        .perform(get("/rents/" + 1).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  // ----- postRent() tests -----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void postRentTest_SimpleBikeRent() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(rent1);

    when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.of(bike1));
    mockMvc
        .perform(
            post("/rents/upload")
                .param("user_id", "100").param("car_id", "0").param("motobike_id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void postRentTest_SimpleCarRent() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(rent1);

    when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    when(carRepository.findById(car1.getId())).thenReturn(Optional.of(car1));
    mockMvc
        .perform(
            post("/rents/upload")
                .param("user_id", "100").param("car_id", "1").param("motobike_id", "0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }
  

  // -----putRent() tests-----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"AUTHOR"})
  public void putRentTest_NotExistingRent() throws Exception {
    // parsing into json for the request body
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(rent1);

    when(rentRepository.findById(rent1.getId())).thenReturn(Optional.empty());

    mockMvc
        .perform(
            put("/rents/" + rent1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isNotFound());
  }


  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void putRentTest_ExistingRent() throws Exception {
    // parsing into json for the request body
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(rent1);

    when(rentRepository.findById(rent1.getId())).thenReturn(Optional.of(rent1));

    mockMvc
        .perform(
            put("/rents/" + rent1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }

   // ----- deleteRent() test -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"AUTHOR"})
   public void deleteRentTest_PresentRent() throws Exception {
 
     when(rentRepository.findById(rent1.getId())).thenReturn(Optional.of(rent1));
 
     mockMvc
         .perform(delete("/rents/" + rent1.getId()).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());
   }
 
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"AUTHOR"})
   public void deleteRentTest_NotPresentRent() throws Exception {
 
     when(rentRepository.findById(rent1.getId())).thenReturn(Optional.empty());
 
     mockMvc
         .perform(delete("/rents/" + 69).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isNotFound());
   }
 

}