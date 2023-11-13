package com.parkingapp.parkingservice.model;




import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.util.UUID;

@Entity
public class City {

    @Id
    private UUID id;
    private String name;

    public City() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
