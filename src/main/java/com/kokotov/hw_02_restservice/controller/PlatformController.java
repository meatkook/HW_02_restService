package com.kokotov.hw_02_restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotov.hw_02_restservice.dto.PlatformDto;
import com.kokotov.hw_02_restservice.service.PlatformService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PlatformServlet", urlPatterns = "/api/platforms/*")
public class PlatformController extends HttpServlet {
    private PlatformService platformService;
    private ObjectMapper objectMapper;

    public void init() {
        platformService = new PlatformService();
        objectMapper = new ObjectMapper();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<PlatformDto> platforms = platformService.getAll();
            String platformJson = objectMapper.writeValueAsString(platforms);
            response.getWriter().write(platformJson);
        } else {
            Long id = Long.parseLong(pathInfo.substring(1));
            PlatformDto platform = platformService.getPlatformById(id);
            if (platform != null) {
                String developerJson = objectMapper.writeValueAsString(platform);
                response.getWriter().write(developerJson);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PlatformDto newPlatform = objectMapper.readValue(request.getReader(), PlatformDto.class);
        PlatformDto createdPlatform = platformService.createPlatform(newPlatform);
        response.getWriter().write(objectMapper.writeValueAsString(createdPlatform));
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        PlatformDto updatedPlatform = objectMapper.readValue(request.getReader(), PlatformDto.class);
        updatedPlatform.setId(id);
        PlatformDto createdPlatform = platformService.updatePlatform(updatedPlatform);
        response.getWriter().write(objectMapper.writeValueAsString(createdPlatform));
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        platformService.deletePlatform(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
