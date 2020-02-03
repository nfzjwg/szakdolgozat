package com.program.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.program.demo.model.Cars;
import com.program.demo.model.Motobikes;
import com.program.demo.model.Receipt;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
import com.program.demo.repositories.CarsRepository;
import com.program.demo.repositories.ReceiptRepository;
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
public class ReceiptControllerTest {
    User testUser;
    Motobikes bike1;
    Cars car1;
    Receipt receipt1;
    Receipt receip2;
    List<Receipt> allReceipt;
    @Autowired private MockMvc mockMvc;
    @MockBean private ReceiptRepository receiptRepository;
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

        receipt1 = new Receipt();
        receipt1.setId(1);
        receipt1.setStart(Date.valueOf("2019-12-01"));
        receipt1.setEnd(Date.valueOf("2019-12-04"));
        receipt1.setCost(50000);
        receipt1.setMotobike(bike1);
        receipt1.setUser(testUser);

        receip2 = new Receipt();
        receip2.setId(2);
        receip2.setStart(Date.valueOf("2019-12-02"));
        receip2.setEnd(Date.valueOf("2019-12-04"));
        receip2.setMotobike(bike1);
        receip2.setUser(testUser);
        receip2.setCost(60000);
    }

     // ----- GetMotobikes() tests -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getReceiptTest_multipleReceipts() throws Exception {
     ArrayList<Receipt> allReceipt = new ArrayList<Receipt>();
     allReceipt.add(receipt1);
     allReceipt.add(receip2);
     when(receiptRepository.findAll()).thenReturn(allReceipt);
     mockMvc
         .perform(get("/receipts").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[1].id", is(2)));
   }
   @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getReceiptTest_emptyReceipts() throws Exception {
    ArrayList<Receipt> allReceipt = new ArrayList<Receipt>();

    when(receiptRepository.findAll()).thenReturn(allReceipt);

    mockMvc
        .perform(get("/receipts").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getReceiptTest_singleReceipt() throws Exception {
    ArrayList<Receipt> allReceipt = new ArrayList<Receipt>();
    allReceipt.add(receipt1);

    when(receiptRepository.findAll()).thenReturn(allReceipt);

    mockMvc
        .perform(get("/receipts").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)));
  }

   // ----- getReceipt() tests -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getReceiptTest_presentReceipt() throws Exception {
     when(receiptRepository.findById(receipt1.getId())).thenReturn(Optional.of(receipt1));
 
     mockMvc
         .perform(get("/receipts/" + receipt1.getId()).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());
   }
 
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getReceiptTest_notPresentReceipt() throws Exception {
     when(receiptRepository.findById(receipt1.getId())).thenReturn(Optional.empty());
 
     mockMvc
         .perform(get("/receipts/" + 1).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isNotFound());
   }

   // ----- postReceipt() tests -----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void postReceiptTest_SimpleBikeReceipt() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(receipt1);

    when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.of(bike1));
    mockMvc
        .perform(
            post("/receipts/upload")
                .param("user_id", "100").param("car_id", "0").param("motobike_id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void postReceiptTest_SimpleCarReceipt() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(receipt1);

    when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    when(carRepository.findById(car1.getId())).thenReturn(Optional.of(car1));
    mockMvc
        .perform(
            post("/receipts/upload")
                .param("user_id", "100").param("car_id", "1").param("motobike_id", "0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }

   // ----- deleteReceipt() test -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"AUTHOR"})
   public void deleteReceiptsTest_PresentReceipts() throws Exception {
 
     when(receiptRepository.findById(receipt1.getId())).thenReturn(Optional.of(receipt1));
 
     mockMvc
         .perform(delete("/receipts/" + receipt1.getId()).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());
   }
 
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"AUTHOR"})
   public void deleteReceiptsTest_NotPresentReceipts() throws Exception {
 
     when(receiptRepository.findById(receipt1.getId())).thenReturn(Optional.empty());
 
     mockMvc
         .perform(delete("/receipts/" + 69).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isNotFound());
   }
}