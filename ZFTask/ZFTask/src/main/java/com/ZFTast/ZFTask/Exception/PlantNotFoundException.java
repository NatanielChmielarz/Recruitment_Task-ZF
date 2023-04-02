package com.ZFTast.ZFTask.Exception;

public class PlantNotFoundException extends RuntimeException {
    public PlantNotFoundException(Long id) {
        super("Could not found the plant with" + id);
    }
}
