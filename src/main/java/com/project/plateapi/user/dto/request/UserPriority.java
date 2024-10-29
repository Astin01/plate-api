package com.project.plateapi.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPriority {
    private String taste;
    private String price;
    private String service;
    private String fresh;
    private String interior;
    private String quantity;
    private String group;
    private String special;
    private String clean;
}