package com.kokotov.hw_02_restservice.service;

import com.kokotov.hw_02_restservice.dto.PlatformSupportDto;
import com.kokotov.hw_02_restservice.entity.PlatformSupport;
import com.kokotov.hw_02_restservice.mapper.Mapper;
import com.kokotov.hw_02_restservice.mapper.impl.PlatformSupportMapper;
import com.kokotov.hw_02_restservice.repository.impl.PlatformSupportRepository;
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