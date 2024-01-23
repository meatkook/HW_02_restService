package com.aston.restservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class GameDto {
    private Long id;
    private String name;
    private DeveloperDto developer;
    private List<PlatformDto> platforms;
}