package com.aston.restservice.mapper.impl;

import com.aston.restservice.dto.PlatformSupportDto;
import com.aston.restservice.entity.PlatformSupport;
import com.aston.restservice.mapper.Mapper;

public class PlatformSupportMapper implements Mapper<PlatformSupportDto, PlatformSupport> {
    @Override
    public PlatformSupportDto toDto(PlatformSupport platformSupport) {
        PlatformSupportDto dto = new PlatformSupportDto();
        dto.setId(platformSupport.getId());
        dto.setGameId(platformSupport.getGameId());
        dto.setPlatformId(platformSupport.getPlatformId());
        return dto;
    }

    @Override
    public PlatformSupport toEntity(PlatformSupportDto dto) {
        PlatformSupport platformSupport = new PlatformSupport();
        platformSupport.setId(dto.getId());
        platformSupport.setGameId(dto.getGameId());
        platformSupport.setPlatformId(dto.getPlatformId());
        return platformSupport;
    }
}