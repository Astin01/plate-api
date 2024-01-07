package com.project.plateapi.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "restaurant")
public class Restaurant {

    protected Restaurant() {

    }
    public Restaurant(String name, String category) {
        this.name = name;
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min=1, message = "Name should have atleast 1 characters")
    private String name;

    private String category;

    public void update(String name, String category){
        this.name=name;
        this.category=category;
    }
}
