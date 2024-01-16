package com.kokotov.hw_02_restservice.mapper.impl;

import com.kokotov.hw_02_restservice.dto.GameDto;
import com.kokotov.hw_02_restservice.dto.PlatformDto;
import com.kokotov.hw_02_restservice.entity.Game;
import com.kokotov.hw_02_restservice.entity.Platform;
import com.kokotov.hw_02_restservice.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

public class GameMapper implements Mapper<GameDto, Game> {
    @Override
    public GameDto toDto(Game game) {
        GameDto dto = new GameDto();

        dto.setId(game.getId());
        dto.setName(game.getName());

        DeveloperMapper devMapper = new DeveloperMapper();
        dto.setDeveloper(devMapper.toDto(game.getDeveloper()));

        PlatformMapper platformMapper = new PlatformMapper();
        List<PlatformDto> platformsDto = new ArrayList<>();
        List<Platform> platforms = game.getPlatforms();
        for(Platform platform : platforms){
            platformsDto.add(platformMapper.toDto(platform));
        }
        dto.setPlatforms(platformsDto);

        return dto;
    }

    @Override
    public Game toEntity(GameDto dto) {
        Game game = new Game();
        game.setId(dto.getId());
        game.setName(dto.getName());

        DeveloperMapper devMapper = new DeveloperMapper();
        if(dto.getDeveloper() != null) {
            game.setDeveloper(devMapper.toEntity(dto.getDeveloper()));
        }

        PlatformMapper platformMapper = new PlatformMapper();
        List<Platform> platforms = new ArrayList<>();
        List<PlatformDto> platformsDto = dto.getPlatforms();
        if(platformsDto == null){
            platformsDto = new ArrayList<>();
        }
        for(PlatformDto platformDto : platformsDto){
            platforms.add(platformMapper.toEntity(platformDto));
        }
        game.setPlatforms(platforms);

        return game;
    }
}

