package com.program.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.program.demo.model.Cars;
import com.program.demo.model.Favourites;
import com.program.demo.model.Motobikes;
import com.program.demo.model.User;
import com.program.demo.repositories.BikesRepository;
import com.program.demo.repositories.CarsRepository;
import com.program.demo.repositories.FavouritesRepository;
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
public class FavouritesControllerTest {
    User testUser;
    Motobikes bike1;
    Cars car1;
    Favourites fav1;
    Favourites fav2;
    List<Favourites> allFavourites;
    @Autowired private MockMvc mockMvc;
    @MockBean private FavouritesRepository favRepository;
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

        fav1 = new Favourites();
        fav1.setId(1);
        fav1.setUser(testUser);
        fav1.setFavouriteBike(bike1);
        
        fav2 = new Favourites();
        fav2.setId(2);
        fav2.setUser(testUser);
        fav2.setFavouriteCar(car1);
    }

     // ----- GetFavourites() tests -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getFavouritesTest_multipleFavourites() throws Exception {
     ArrayList<Favourites> allFavourites = new ArrayList<Favourites>();
     allFavourites.add(fav1);
     allFavourites.add(fav2);
     when(favRepository.findAll()).thenReturn(allFavourites);
     mockMvc
         .perform(get("/favourites").contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$", hasSize(2)))
         .andExpect(jsonPath("$[0].id", is(1)))
         .andExpect(jsonPath("$[1].id", is(2)));
   }
   @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getFavouritesTest_emptyFavourites() throws Exception {
    ArrayList<Favourites> allFavourites = new ArrayList<Favourites>();

    when(favRepository.findAll()).thenReturn(allFavourites);

    mockMvc
        .perform(get("/favourites").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void getFavouritesTest_singleFavourites() throws Exception {
    ArrayList<Favourites> allFavourites = new ArrayList<Favourites>();
    allFavourites.add(fav1);

    when(favRepository.findAll()).thenReturn(allFavourites);

    mockMvc
        .perform(get("/favourites").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(1)));
  }

   // ----- getFavourite() tests -----
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getFavouritesTest_presentFavourites() throws Exception {
     when(favRepository.findById(fav1.getId())).thenReturn(Optional.of(fav1));
 
     mockMvc
         .perform(get("/favourites/" + fav1.getId()).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk());
   }
 
   @Test
   @WithMockUser(
       username = "admin",
       roles = {"ADMIN"})
   public void getFavouritesTest_notPresentFavourites() throws Exception {
     when(favRepository.findById(fav1.getId())).thenReturn(Optional.empty());
 
     mockMvc
         .perform(get("/favourites/" + 1).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isNotFound());
   }

    // ----- postFavourites() tests -----
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void postFavouritesTest_SimpleBikeFavourites() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(fav1);

    when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    when(bikeRepository.findById(bike1.getId())).thenReturn(Optional.of(bike1));
    mockMvc
        .perform(
            post("/favourites/add")
                .param("user_id", "100").param("car_id", "0").param("motobike_id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }
  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  public void postFavouritesTest_SimpleCarFavourites() throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJson = ow.writeValueAsString(fav1);

    when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
    when(carRepository.findById(car1.getId())).thenReturn(Optional.of(car1));
    mockMvc
        .perform(
            post("/favourites/add")
                .param("user_id", "100").param("car_id", "1").param("motobike_id", "0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk());
  }
 
}
