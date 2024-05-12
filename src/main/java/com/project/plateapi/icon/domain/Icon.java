package com.project.plateapi.icon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "icon")
@Data
public class Icon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LINK")
    private String link;
}
