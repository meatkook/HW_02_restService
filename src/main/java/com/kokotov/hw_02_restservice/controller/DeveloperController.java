package com.kokotov.hw_02_restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotov.hw_02_restservice.dto.DeveloperDto;
import com.kokotov.hw_02_restservice.service.DeveloperService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeveloperServlet", urlPatterns = "/api/developers/*")
public class DeveloperController extends HttpServlet {
    private DeveloperService developerService;
    private ObjectMapper objectMapper;

    public void init() {
        developerService = new DeveloperService();
        objectMapper = new ObjectMapper();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<DeveloperDto> developers = developerService.getAllDevelopers();
            String developersJson = objectMapper.writeValueAsString(developers);
            response.getWriter().write(developersJson);
        } else {
            Long id = Long.parseLong(pathInfo.substring(1));
            DeveloperDto developer = developerService.getDeveloperById(id);
            if (developer != null) {
                String developerJson = objectMapper.writeValueAsString(developer);
                response.getWriter().write(developerJson);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DeveloperDto newDeveloper = objectMapper.readValue(request.getReader(), DeveloperDto.class);
        DeveloperDto createdDeveloper = developerService.createDeveloper(newDeveloper);
        String developerJson = objectMapper.writeValueAsString(createdDeveloper);
        response.getWriter().write(developerJson);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getPathInfo().substring(1));
        DeveloperDto updatedDeveloper = objectMapper.readValue(request.getReader(), DeveloperDto.class);
        updatedDeveloper.setId(id);
        DeveloperDto modifiedDeveloper = developerService.updateDeveloper(updatedDeveloper);
        String developerJson = objectMapper.writeValueAsString(modifiedDeveloper);
        response.getWriter().write(developerJson);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getPathInfo().substring(1));
        developerService.deleteDeveloper(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}