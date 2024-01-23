package com.aston.restservice.service;

import com.aston.restservice.mapper.Mapper;
import com.aston.restservice.mapper.impl.DeveloperMapper;
import com.aston.restservice.dto.DeveloperDto;
import com.aston.restservice.entity.Developer;
import com.aston.restservice.repository.Repository;
import com.aston.restservice.repository.impl.DeveloperRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
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