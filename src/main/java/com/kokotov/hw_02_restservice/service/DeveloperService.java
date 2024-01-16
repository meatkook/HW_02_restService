package com.kokotov.hw_02_restservice.service;

import com.kokotov.hw_02_restservice.dto.DeveloperDto;
import com.kokotov.hw_02_restservice.entity.Developer;
import com.kokotov.hw_02_restservice.mapper.Mapper;
import com.kokotov.hw_02_restservice.mapper.impl.DeveloperMapper;
import com.kokotov.hw_02_restservice.repository.Repository;
import com.kokotov.hw_02_restservice.repository.impl.DeveloperRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperService {
    private final Repository<Developer, Long> repository;
    private final Mapper<DeveloperDto, Developer> mapper;

    public DeveloperService() {
        this.repository = new DeveloperRepository();
        this.mapper = new DeveloperMapper();
    }

    public DeveloperDto getDeveloperById(Long id) {
        Optional<Developer> developer = repository.findById(id);
        return developer.map(mapper::toDto).orElse(null);
    }

    public DeveloperDto createDeveloper(DeveloperDto developerDTO) {
        Developer developer = mapper.toEntity(developerDTO);
        return mapper.toDto(repository.save(developer));
    }

    public DeveloperDto updateDeveloper(DeveloperDto developerDTO) {
        Developer developer = mapper.toEntity(developerDTO);
        return mapper.toDto(repository.update(developer));
    }

    public void deleteDeveloper(Long id) {
        repository.deleteById(id);
    }

    public List<DeveloperDto> getAllDevelopers() {
        Iterable<Developer> developers = repository.findAll();
        List<DeveloperDto> developerDTOs = new ArrayList<>();
        for (Developer developer : developers) {
            developerDTOs.add(mapper.toDto(developer));
        }
        return developerDTOs;
    }
}