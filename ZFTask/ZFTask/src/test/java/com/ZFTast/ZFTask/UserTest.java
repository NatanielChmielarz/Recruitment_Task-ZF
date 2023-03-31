package com.ZFTast.ZFTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import com.ZFTast.ZFTask.Model.Plant;
import com.ZFTast.ZFTask.Model.User;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testUserName() {
        User user = new User();
        user.setUsername("TestUser");
        assertEquals("TestUser", user.getUsername());
    }

    @Test
    public void testUserPlants() {
        User user = new User();
        Plant plant1 = new Plant("TestPlant1", "TestSpecies", "TestDescription", "1");
        Plant plant2 = new Plant("TestPlant2", "TestSpecies", "TestDescription", "1");
        List<Plant> plants = new ArrayList<Plant>();
        plants.add(plant1);
        plants.add(plant2);
        user.setPlants(plants);
        assertEquals(2, user.getPlants().size());
    }
}
