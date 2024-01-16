package com.kokotov.hw_02_restservice.repository;

import java.util.Optional;

public interface Repository<Entity, ID> {
    Optional<Entity> findById(ID id);
    Iterable<Entity> findAll();
    Entity save(Entity entity);
    Entity update(Entity entity);
    void deleteById(ID id);
}
