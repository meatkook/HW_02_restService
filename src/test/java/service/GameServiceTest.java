package service;

import com.kokotov.hw_02_restservice.dto.GameDto;
import com.kokotov.hw_02_restservice.entity.Game;
import com.kokotov.hw_02_restservice.mapper.impl.GameMapper;
import com.kokotov.hw_02_restservice.repository.impl.GameRepository;
import com.kokotov.hw_02_restservice.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void init() {
        gameService = new GameService(gameRepository, gameMapper);
    }

    @Test
    void testGetGameById() {
        Long id = 1L;
        Game game = new Game();
        GameDto gameDto = new GameDto();
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));
        when(gameMapper.toDto(game)).thenReturn(gameDto);

        GameDto result = gameService.getGameById(id);

        assertEquals(gameDto, result);
    }

    @Test
    void testCreateGame() {
        GameDto gameDto = new GameDto();
        Game game = new Game();
        when(gameMapper.toEntity(gameDto)).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);
        when(gameMapper.toDto(game)).thenReturn(gameDto);

        GameDto result = gameService.createGame(gameDto);

        assertEquals(gameDto, result);
    }

    @Test
    void testUpdateGame() {
        GameDto gameDto = new GameDto();
        Game game = new Game();
        when(gameMapper.toEntity(gameDto)).thenReturn(game);

        gameService.updateGame(gameDto);

        verify(gameRepository, times(1)).update(game);
    }

    @Test
    void testDeleteGame() {
        Long id = 1L;
        gameService.deleteGame(id);
        verify(gameRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAllGames() {
        Game game = new Game();
        GameDto gameDto = new GameDto();
        when(gameMapper.toDto(game)).thenReturn(gameDto);
        when(gameRepository.findAll()).thenReturn(Arrays.asList(game, game, game));

        List<GameDto> result = gameService.getAllGames();

        assertEquals(3, result.size());
        assertEquals(gameDto, result.get(0));
        assertEquals(gameDto, result.get(1));
        assertEquals(gameDto, result.get(2));
    }
}