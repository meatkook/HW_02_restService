package com.kokotov.hw_02_restservice.mapper.impl;

import com.kokotov.hw_02_restservice.dto.DeveloperDto;
import com.kokotov.hw_02_restservice.entity.Developer;
import com.kokotov.hw_02_restservice.mapper.Mapper;

public class DeveloperMapper implements Mapper<DeveloperDto, Developer> {
    @Override
    public DeveloperDto toDto(Developer developer) {
        DeveloperDto dto = new DeveloperDto();
        dto.setId(developer.getId());
        dto.setDeveloperName(developer.getName());
        return dto;
    }

    @Override
    public Developer toEntity(DeveloperDto dto) {
        Developer developer = new Developer();
        developer.setId(dto.getId());
        developer.setName(dto.getDeveloperName());
        return developer;
    }
}