package com.aston.restservice.service;

import com.aston.restservice.mapper.Mapper;
import com.aston.restservice.mapper.impl.PlatformMapper;
import com.aston.restservice.dto.PlatformDto;
import com.aston.restservice.entity.Platform;
import com.aston.restservice.repository.Repository;
import com.aston.restservice.repository.impl.PlatformRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
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