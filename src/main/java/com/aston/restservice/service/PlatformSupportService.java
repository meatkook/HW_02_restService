package com.aston.restservice.service;

import com.aston.restservice.mapper.Mapper;
import com.aston.restservice.dto.PlatformSupportDto;
import com.aston.restservice.entity.PlatformSupport;
import com.aston.restservice.mapper.impl.PlatformSupportMapper;
import com.aston.restservice.repository.impl.PlatformSupportRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlatformSupportService {
    private final PlatformSupportRepository repository;
    private final Mapper<PlatformSupportDto, PlatformSupport> mapper;

    public PlatformSupportService() {
        this.repository = new PlatformSupportRepository();
        this.mapper = new PlatformSupportMapper();
    }

    public void create(PlatformSupportDto supportDto) {
        PlatformSupport support = mapper.toEntity(supportDto);
        mapper.toDto(repository.save(support));
    }

    public void delete(Long gameId, Long platformId) {
        repository.deleteById(gameId, platformId);
    }
}