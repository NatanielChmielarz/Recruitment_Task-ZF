package com.ZFTast.ZFTask.Contoller;

import com.ZFTast.ZFTask.Exception.PlantNotFoundException;
import com.ZFTast.ZFTask.Exception.UserNotFoundException;
import com.ZFTast.ZFTask.Model.Plant;
import com.ZFTast.ZFTask.Model.PlantRequest;
import com.ZFTast.ZFTask.Model.User;
import com.ZFTast.ZFTask.Repository.PlantRepository;
import com.ZFTast.ZFTask.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000/")
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/api/plant")
    public ResponseEntity<Plant> createPlant(@RequestBody PlantRequest request) {
        Plant plant = plantRepository.createPlant(request.getName(), request.getSpecies(), request.getDescription(), request.getUserId());
        return ResponseEntity.ok(plant);
    }

    @GetMapping("/api/plants")
    List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    @GetMapping("/api/plant/{id}")
    Plant getPlantById(@PathVariable Long id) {
        return plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException(id));
    }

    @PutMapping("/api/plant/{id}")
    Plant updatePlant(@RequestBody Plant newPlant, @PathVariable Long id) {
        return plantRepository.findById(id)
                .map(plant -> {
                    plant.setDescription(newPlant.getDescription());
                    plant.setName(newPlant.getName());
                    plant.setSpecies(newPlant.getSpecies());
                    return plantRepository.save(plant);
                }).orElseThrow(() -> new PlantNotFoundException(id));
    }


    @DeleteMapping("/api/plant/{id}")
    String deletePlant(@PathVariable Long id) {
        if (!plantRepository.existsById(id)) {
            throw new PlantNotFoundException(id);
        }
        plantRepository.deleteById(id);
        return "Plant with id " + id + " has been deleted success.";
    }

    public void setPlantRepository(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }
}
