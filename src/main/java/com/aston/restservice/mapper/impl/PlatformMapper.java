package com.aston.restservice.mapper.impl;

import com.aston.restservice.dto.PlatformDto;
import com.aston.restservice.entity.Platform;
import com.aston.restservice.mapper.Mapper;

public class PlatformMapper implements Mapper<PlatformDto, Platform> {
    @Override
    public PlatformDto toDto(Platform platform) {
        PlatformDto dto = new PlatformDto();
        dto.setId(platform.getId());
        dto.setName(platform.getName());
        return dto;
    }

    @Override
    public Platform toEntity(PlatformDto dto) {
        Platform platform = new Platform();
        platform.setId(dto.getId());
        platform.setName(dto.getName());
        return platform;
    }
}