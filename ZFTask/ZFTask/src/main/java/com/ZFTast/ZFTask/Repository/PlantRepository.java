package com.ZFTast.ZFTask.Repository;

import com.ZFTast.ZFTask.Model.Plant;
import com.ZFTast.ZFTask.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant,Long> {

    @Transactional
    default Plant createPlant(String name, String species, String description, String userId) {

        User user = new User();
        user.setId(Long.parseLong(userId));
        Plant plant = new Plant(name, species, description, userId);
        plant.setUser(user);
        return save(plant);
    }
}
