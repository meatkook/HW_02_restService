package com.kokotov.hw_02_restservice.controller;

import com.kokotov.hw_02_restservice.dto.PlatformSupportDto;
import com.kokotov.hw_02_restservice.service.PlatformSupportService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/games/platformSupport/*")
public class PlatformSupportController extends HttpServlet {
    private final PlatformSupportService platformSupportService = new PlatformSupportService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String[] pathParts = request.getPathInfo().split("/");
        if (pathParts.length == 3) {
            Long gameId = Long.parseLong(pathParts[1]);
            Long platformId = Long.parseLong(pathParts[2]);
            PlatformSupportDto supportDto = new PlatformSupportDto(gameId, platformId);
            platformSupportService.create(supportDto);
            response.setStatus(HttpServletResponse.SC_CREATED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String[] pathParts = request.getPathInfo().split("/");
        if (pathParts.length == 3) {
            Long gameId = Long.parseLong(pathParts[1]);
            Long platformId = Long.parseLong(pathParts[2]);
            platformSupportService.delete(gameId, platformId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}