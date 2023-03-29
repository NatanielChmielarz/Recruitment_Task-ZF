package com.ZFTast.ZFTask.Contoller;

import com.ZFTast.ZFTask.Exception.PlantNotFoundException;
import com.ZFTast.ZFTask.Model.Plant;
import com.ZFTast.ZFTask.Repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000/")
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    @PostMapping("/plant")
    Plant newPlant(@RequestBody Plant newPlant){
        return plantRepository.save(newPlant);
    }

    @GetMapping("/plants")
    List<Plant> getAllPlants(){
        return plantRepository.findAll();
    }


}
