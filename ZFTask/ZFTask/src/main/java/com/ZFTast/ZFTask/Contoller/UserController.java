package com.ZFTast.ZFTask.Contoller;

import com.ZFTast.ZFTask.Exception.UserNotFoundException;
import com.ZFTast.ZFTask.Model.Plant;
import com.ZFTast.ZFTask.Model.User;
import com.ZFTast.ZFTask.Repository.PlantRepository;
import com.ZFTast.ZFTask.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000/")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlantRepository plantRepository;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @GetMapping(value = "/users")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(id);
        }
        User user = userOptional.get();
        List<Plant> plants = user.getPlants();
        if(!plants.isEmpty()){
            plantRepository.deleteAll(plants);
        }
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted successfully.";
    }

}
