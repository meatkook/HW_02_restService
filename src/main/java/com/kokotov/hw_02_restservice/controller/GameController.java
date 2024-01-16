package com.kokotov.hw_02_restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotov.hw_02_restservice.dto.GameDto;
import com.kokotov.hw_02_restservice.dto.PlatformSupportDto;
import com.kokotov.hw_02_restservice.service.GameService;
import com.kokotov.hw_02_restservice.service.PlatformSupportService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GameServlet", urlPatterns = "/api/games/*")
public class GameController extends HttpServlet {
    private GameService gameService;
    private PlatformSupportService supportService;
    private ObjectMapper objectMapper;
    public void init() {
        gameService = new GameService();
        supportService = new PlatformSupportService();
        objectMapper = new ObjectMapper();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<GameDto> games = gameService.getAllGames();
            String developersJson = objectMapper.writeValueAsString(games);
            response.getWriter().write(developersJson);
        } else {
            Long id = Long.parseLong(pathInfo.substring(1));
            GameDto game = gameService.getGameById(id);
            if (game != null) {
                String gameJson = objectMapper.writeValueAsString(game);
                response.getWriter().write(gameJson);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        GameDto newGame = objectMapper.readValue(request.getReader(), GameDto.class);
        newGame = gameService.createGame(newGame);

        if (pathInfo != null) {
            Long platformId = Long.parseLong(pathInfo.substring(1));
            PlatformSupportDto supportDto = new PlatformSupportDto(newGame.getId(), platformId);
            supportService.create(supportDto);
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo.matches("/\\d+")) {
            Long id = Long.parseLong(pathInfo.substring(1));
            GameDto updatedGame = objectMapper.readValue(request.getReader(), GameDto.class);
            updatedGame.setId(id);
            gameService.updateGame(updatedGame);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        if (pathInfo.matches("/\\d+")) {
            Long id = Long.parseLong(pathInfo.substring(1));
            gameService.deleteGame(id);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}