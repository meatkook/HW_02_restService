package com.aston.restservice.controller;

import com.aston.restservice.service.PlatformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aston.restservice.dto.PlatformDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PlatformServlet", urlPatterns = "/api/platforms/*")
public class PlatformController extends HttpServlet {
    private final PlatformService platformService;
    private final ObjectMapper objectMapper;

    public PlatformController() {
        this.platformService = new PlatformService();
        this.objectMapper = new ObjectMapper();
    }

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
        this.objectMapper = new ObjectMapper();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PlatformDto newPlatform = objectMapper.readValue(request.getReader(), PlatformDto.class);
        PlatformDto createdPlatform = platformService.createPlatform(newPlatform);
        response.getWriter().write(objectMapper.writeValueAsString(createdPlatform));
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        PlatformDto updatedPlatform = objectMapper.readValue(request.getReader(), PlatformDto.class);
        updatedPlatform.setId(id);
        PlatformDto createdPlatform = platformService.updatePlatform(updatedPlatform);
        response.getWriter().write(objectMapper.writeValueAsString(createdPlatform));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = request.getPathInfo();
        Long id = Long.parseLong(pathInfo.substring(1));
        platformService.deletePlatform(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
