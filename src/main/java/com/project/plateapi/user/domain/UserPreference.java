package com.project.plateapi.user.domain;

import com.project.plateapi.user.domain.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_preference")
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal taste;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal service;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal fresh;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal interior;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal quantity;

    @Column(name = "`group`", nullable = false, precision = 3, scale = 2)
    private BigDecimal group;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal special;

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal clean;
}
