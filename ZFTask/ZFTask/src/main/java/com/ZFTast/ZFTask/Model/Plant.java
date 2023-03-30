package com.ZFTast.ZFTask.Model;

import jakarta.persistence.*;
@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plant_id;
    private String name;
    private String species;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" )
    private  User user;

    public Plant(String name, String species, String description, String user_id) {

        this.name = name;
        this.species = species;
        this.description = description;
        user= new User();
        this.user.setId(Long.parseLong(user_id));
    }
    private Plant(){}
    public Long getId() {
        return plant_id;
    }

    public void setId(Long id) {
        this.plant_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user=user;
    }
}


