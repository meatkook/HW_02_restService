package com.aston.restservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private Long id;
    private String name;
    private Developer developer;
    private List<Platform> platforms;
}