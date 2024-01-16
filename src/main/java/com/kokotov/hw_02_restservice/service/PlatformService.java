package com.kokotov.hw_02_restservice.service;

import com.kokotov.hw_02_restservice.dto.PlatformDto;
import com.kokotov.hw_02_restservice.entity.Platform;
import com.kokotov.hw_02_restservice.mapper.Mapper;
import com.kokotov.hw_02_restservice.mapper.impl.PlatformMapper;
import com.kokotov.hw_02_restservice.repository.Repository;
import com.kokotov.hw_02_restservice.repository.impl.PlatformRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlatformService {
    private final Repository<Platform, Long> repository;
    private final Mapper<PlatformDto, Platform> mapper;

    public PlatformService() {
        this.repository = new PlatformRepository();
        this.mapper = new PlatformMapper();
    }

    public PlatformDto getPlatformById(Long id) {
        Optional<Platform> platform = repository.findById(id);
        return platform.map(mapper::toDto).orElse(null);
    }

    public List<PlatformDto> getAll() {
        Iterable<Platform> platforms = repository.findAll();
        List<PlatformDto> platformsDto = new ArrayList<>();
        for (Platform platform : platforms) {
            platformsDto.add(mapper.toDto(platform));
        }
        return platformsDto;
    }

    public PlatformDto createPlatform(PlatformDto platformDTO) {
        Platform platform = mapper.toEntity(platformDTO);
        return mapper.toDto(repository.save(platform));
    }

    public PlatformDto updatePlatform(PlatformDto platformDTO) {
        Platform platform = mapper.toEntity(platformDTO);
        return mapper.toDto(repository.update(platform));
    }

    public void deletePlatform(Long id) {
        repository.deleteById(id);
    }
}
