package com.project.plateapi.icon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "icon")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
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

    public void update(String icon, String name, String link) {
        this.icon = icon;
        this.name = name;
        this.link = link;
    }
}
