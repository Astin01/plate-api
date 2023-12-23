package com.project.plateapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Restaurants {
    @GetMapping(path="/api/restaurants")
    public void getRestaurants(){}
}
