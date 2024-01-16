package com.kokotov.hw_02_restservice.entity;

import lombok.Data;

import java.util.List;

@Data
public class Game {
    private Long id;
    private String name;
    private Developer developer;
    List<Platform> platforms;
}
