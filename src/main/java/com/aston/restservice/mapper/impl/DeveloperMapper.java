package com.aston.restservice.mapper.impl;

import com.aston.restservice.mapper.Mapper;
import com.aston.restservice.dto.DeveloperDto;
import com.aston.restservice.entity.Developer;

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