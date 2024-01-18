package com.kokotov.hw_02_restservice.mapper.impl;

import com.kokotov.hw_02_restservice.dto.PlatformSupportDto;
import com.kokotov.hw_02_restservice.entity.PlatformSupport;
import com.kokotov.hw_02_restservice.mapper.Mapper;

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
