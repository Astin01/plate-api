package com.project.plateapi.restaurant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Size(min = 1, message = "Name should have at least 1 characters")
    @Column(name = "NAME")
    private String name;

    @Column(name="CATEGORY")
    private String category;

    @Column(name="ICON")
    private String icon;

    @Column(name="CONTENT",columnDefinition = "TEXT")
    private  String content;

    public void update(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.category = restaurant.getCategory();
        this.icon = restaurant.getIcon();
        this.content = restaurant.getContent();
    }

}
