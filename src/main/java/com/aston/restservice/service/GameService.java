package com.aston.restservice.service;

import com.aston.restservice.mapper.Mapper;
import com.aston.restservice.mapper.impl.GameMapper;
import com.aston.restservice.dto.GameDto;
import com.aston.restservice.entity.Game;
import com.aston.restservice.repository.impl.GameRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final Mapper<GameDto, Game> gameMapper;

    public GameService() {
        this.gameRepository = new GameRepository();
        this.gameMapper = new GameMapper();
    }

    public GameDto getGameById(Long id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.map(gameMapper::toDto).orElse(null);
    }

    public List<GameDto> getAllGames() {
        Iterable<Game> games = gameRepository.findAll();
        List<GameDto> gamesDto = new ArrayList<>();
        for (Game game : games) {
            gamesDto.add(gameMapper.toDto(game));
        }
        return gamesDto;
    }

    public GameDto createGame(GameDto gameDTO) {
        Game game = gameMapper.toEntity(gameDTO);
        return gameMapper.toDto(gameRepository.save(game));
    }

    public void updateGame(GameDto gameDTO) {
        Game game = gameMapper.toEntity(gameDTO);
        gameRepository.update(game);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}