package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.aston.restservice.controller.GameController;
import com.aston.restservice.dto.DeveloperDto;
import com.aston.restservice.dto.GameDto;
import com.aston.restservice.dto.PlatformDto;
import com.aston.restservice.dto.PlatformSupportDto;
import com.aston.restservice.service.GameService;
import com.aston.restservice.service.PlatformSupportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameControllerTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private GameController controller;
    private GameService gameService;
    private PlatformSupportService supportService;
    private ObjectMapper mapper;

    private List<GameDto> games;

    @BeforeEach
    void setMocks() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        gameService = Mockito.mock(GameService.class);
        supportService = Mockito.mock(PlatformSupportService.class);
        controller = new GameController(gameService, supportService);
        mapper = new ObjectMapper();
        games = new ArrayList<>();

        GameDto game1 = new GameDto();
        game1.setId(1L);
        game1.setName("The Witcher 3");
        game1.setDeveloper(new DeveloperDto(1L, "CD Projekt Red"));
        game1.setPlatforms(new ArrayList<>(List.of(new PlatformDto(1L, "PC"))));

        GameDto game2 = new GameDto();
        game2.setId(2L);
        game2.setName("Red Dead Redemption 2");
        game2.setDeveloper(new DeveloperDto(2L, "Rockstar Games"));
        game2.setPlatforms(new ArrayList<>(List.of(new PlatformDto(2L, "PC"))));

        games.add(game1);
        games.add(game2);
    }

    @Test
    void getAllGames() throws IOException {

        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/");
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(gameService.getAllGames()).thenReturn(games);

        controller.doGet(request, response);
        printWriter.flush();
        assertEquals(mapper.writeValueAsString(games), writer.toString());
    }

    @Test
    void getGameById() throws IOException {
        GameDto game = games.get(0);
        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(gameService.getGameById(1L)).thenReturn(game);

        controller.doGet(request, response);
        printWriter.flush();
        assertEquals(mapper.writeValueAsString(game), writer.toString());
    }

    @Test
    void addGame() throws IOException {
        GameDto game = games.get(0);
        String json = mapper.writeValueAsString(game);

        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(gameService.createGame(ArgumentMatchers.any(GameDto.class))).thenReturn(game);

        controller.doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
        Mockito.verify(gameService).createGame(ArgumentMatchers.any(GameDto.class));
        assertEquals("", writer.toString());
        Mockito.verify(supportService).create(ArgumentMatchers.any(PlatformSupportDto.class));
    }

    @Test
    void updateGame() throws IOException {
        GameDto updateGame = new GameDto();
        String json = mapper.writeValueAsString(updateGame);
        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        controller.doPut(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        Mockito.verify(gameService).updateGame(ArgumentMatchers.any(GameDto.class));
        assertEquals("", writer.toString());
    }

    @Test
    void deleteGame() {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        controller.doDelete(request, response);

        Mockito.verify(gameService).deleteGame(1L);
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}