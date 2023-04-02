package com.ZFTast.ZFTask.Model;

public class PlantRequest {

    private String name;
    private String species;
    private String description;
    private String user_id;

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


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUserId() {
        return user_id;
    }


}
