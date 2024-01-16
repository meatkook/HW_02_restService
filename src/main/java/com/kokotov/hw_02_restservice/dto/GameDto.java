package com.kokotov.hw_02_restservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameDto {
    private Long id;
    private String name;
    private DeveloperDto developer;
    List<PlatformDto> platforms;
}