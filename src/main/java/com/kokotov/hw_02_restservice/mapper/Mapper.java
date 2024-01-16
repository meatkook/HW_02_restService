package com.kokotov.hw_02_restservice.mapper;

public interface Mapper<DTO, Entity> {
    DTO toDto(Entity entity);
    Entity toEntity(DTO dto);
}
