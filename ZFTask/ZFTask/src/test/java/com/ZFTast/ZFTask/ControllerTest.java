package com.ZFTast.ZFTask;

import com.ZFTast.ZFTask.Contoller.PlantController;
import com.ZFTast.ZFTask.Contoller.UserController;
import com.ZFTast.ZFTask.Exception.UserNotFoundException;
import com.ZFTast.ZFTask.Model.Plant;
import com.ZFTast.ZFTask.Model.PlantRequest;
import com.ZFTast.ZFTask.Model.User;
import com.ZFTast.ZFTask.Repository.PlantRepository;
import com.ZFTast.ZFTask.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {PlantController.class, UserController.class})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantRepository plantRepository;

    @MockBean
    private UserRepository userRepository;

    private ObjectMapper objectMapper;
    private User testUser;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testUser");
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");
    }

    // PlantController tests
    @Test
    public void testCreatePlant() throws Exception {
        // given
        PlantRepository plantRepository = mock(PlantRepository.class);
        PlantController plantController = new PlantController();
        plantController.setPlantRepository(plantRepository);

        PlantRequest plantRequest = new PlantRequest();
        plantRequest.setName("Fiddle leaf fig");
        plantRequest.setSpecies("Ficus lyrata");
        plantRequest.setDescription("This plant likes bright, indirect light and can grow up to 10 feet tall!");
        plantRequest.setUser_id("1");

        Plant createdPlant = new Plant("Fiddle leaf fig", "Ficus lyrata", "This plant likes bright, indirect light and can grow up to 10 feet tall!", "1");
        when(plantRepository.createPlant(eq("Fiddle leaf fig"), eq("Ficus lyrata"), eq("This plant likes bright, indirect light and can grow up to 10 feet tall!"), eq("1")))
                .thenReturn(createdPlant);

        // when
        ResponseEntity<Plant> response = plantController.createPlant(plantRequest);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Plant createdPlantResponse = response.getBody();
        assertNotNull(createdPlantResponse);
        assertEquals("Fiddle leaf fig", createdPlantResponse.getName());
        assertEquals("Ficus lyrata", createdPlantResponse.getSpecies());
        assertEquals("This plant likes bright, indirect light and can grow up to 10 feet tall!", createdPlantResponse.getDescription());
        assertEquals("1", createdPlantResponse.getUserid());
    }

    @Test
    public void testGetAllPlants() throws Exception {
        List<Plant> plants = new ArrayList<>();
        plants.add(new Plant("Fiddle leaf fig", "Ficus lyrata", "This plant likes bright, indirect light and can grow up to 10 feet tall!", "1"));
        plants.add(new Plant("Peace lily", "Spathiphyllum wallisii", "This plant likes low to medium light and can grow up to 2 feet tall!", "1"));

        when(plantRepository.findAll()).thenReturn(plants);

        mockMvc.perform(MockMvcRequestBuilders.get("/plants"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Fiddle leaf fig"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].species").value("Ficus lyrata"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("This plant likes bright, indirect light and can grow up to 10 feet tall!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Peace lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].species").value("Spathiphyllum wallisii"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("This plant likes low to medium light and can grow up to 2 feet tall!"))
                .andDo(MockMvcResultHandlers.print());
    }



    @Test
    public void testUpdatePlant() throws Exception {
        PlantRequest plantRequest = new PlantRequest();
        plantRequest.setName("Fiddle leaf fig");
        plantRequest.setSpecies("Ficus lyrata");
        plantRequest.setDescription("This plant likes bright, indirect light and can grow up to 10 feet tall!");

        Plant existingPlant = new Plant();
        existingPlant.setId(1L);
        existingPlant.setName("Fiddle leaf fig");
        existingPlant.setSpecies("Ficus lyrata");
        existingPlant.setDescription("This plant likes bright, indirect light and can grow up to 10 feet tall!");
        existingPlant.setUser(new User());

        when(plantRepository.findById(1L)).thenReturn(Optional.of(existingPlant));
        when(plantRepository.save(any(Plant.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(MockMvcRequestBuilders.put("/plant/1")
                        .content(objectMapper.writeValueAsString(plantRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fiddle leaf fig"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.species").value("Ficus lyrata"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("This plant likes bright, indirect light and can grow up to 10 feet tall!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeletePlant() throws Exception {
        long plantId = 1L;
        doNothing().when(plantRepository).deleteById(plantId);
        when(plantRepository.existsById(plantId)).thenReturn(true);

        mockMvc.perform(delete("/plant/{id}", plantId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Plant with id " + plantId + " has been deleted success."))
                .andDo(MockMvcResultHandlers.print());

        verify(plantRepository, times(1)).deleteById(plantId);
    }
    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(testUser);
        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(testUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(testUser.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(testUser.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value(testUser.getEmail()));
    }


    @Test
    public void testGetUserById() throws Exception {
        when(userRepository.findById(eq(testUser.getId()))).thenReturn(Optional.of(testUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testUser.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(testUser.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testUser.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(testUser.getEmail()));
    }
    @Test
    public void testGetUserByIdUserNotFoundException() throws Exception {
        when(userRepository.findById(eq(testUser.getId()))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/user/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserNotFoundException))
                .andExpect(result -> assertEquals("Could not found the user with" + testUser.getId(), result.getResolvedException().getMessage()));
    }





    }


