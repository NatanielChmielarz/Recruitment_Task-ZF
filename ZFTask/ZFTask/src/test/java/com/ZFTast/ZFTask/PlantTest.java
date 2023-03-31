package com.ZFTast.ZFTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ZFTast.ZFTask.Model.Plant;
import org.junit.jupiter.api.Test;

public class PlantTest {

    @Test
    public void testPlantName() {
        Plant plant = new Plant("TestPlant", "TestSpecies", "TestDescription", "1");
        assertEquals("TestPlant", plant.getName());
    }

    @Test
    public void testPlantSpecies() {
        Plant plant = new Plant("TestPlant", "TestSpecies", "TestDescription", "1");
        assertEquals("TestSpecies", plant.getSpecies());
    }

    @Test
    public void testPlantDescription() {
        Plant plant = new Plant("TestPlant", "TestSpecies", "TestDescription", "1");
        assertEquals("TestDescription", plant.getDescription());
    }
}