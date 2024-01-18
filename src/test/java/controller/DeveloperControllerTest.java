package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kokotov.hw_02_restservice.controller.DeveloperController;
import com.kokotov.hw_02_restservice.dto.DeveloperDto;
import com.kokotov.hw_02_restservice.service.DeveloperService;
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

class DeveloperControllerTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private DeveloperController controller;
    private DeveloperService service;
    private ObjectMapper mapper;

    @BeforeEach
    void setMocks() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        service = Mockito.mock(DeveloperService.class);
        controller = new DeveloperController(service);
        mapper = new ObjectMapper();
    }

    @Test
    void getAllDevelopers() throws IOException {
        List<DeveloperDto> developers = new ArrayList<>();
        DeveloperDto developer1 = new DeveloperDto(1L, "CD Projekt Red");
        DeveloperDto developer2 = new DeveloperDto(2L, "Rockstar Games");
        developers.add(developer1);
        developers.add(developer2);
        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/");
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(service.getAllDevelopers()).thenReturn(developers);

        controller.doGet(request, response);
        printWriter.flush();
        assertEquals(mapper.writeValueAsString(developers), writer.toString());
    }

    @Test
    void getDeveloperById() throws IOException {
        DeveloperDto developer = new DeveloperDto();
        developer.setId(1L);
        developer.setDeveloperName("CD Projekt Red");
        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(service.getDeveloperById(1L)).thenReturn(developer);

        controller.doGet(request, response);
        printWriter.flush();
        assertEquals(mapper.writeValueAsString(developer), writer.toString());
    }

    @Test
    void addDeveloper() throws IOException {
        String json = "{\"id\":1,\"developerName\":\"CD Projekt Red\"}";
        DeveloperDto dto = new DeveloperDto(1L, "CD Projekt Red");
        StringWriter writer = new StringWriter();

        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(service.createDeveloper(ArgumentMatchers.any(DeveloperDto.class))).thenReturn(dto);

        controller.doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
        Mockito.verify(service).createDeveloper(ArgumentMatchers.any(DeveloperDto.class));
        assertEquals(mapper.writeValueAsString(dto), writer.toString());
    }

    @Test
    void updateDeveloper() throws IOException {
        String json = "{\"id\":1,\"developerName\":\"CD Projekt Red\"}";
        DeveloperDto updatedDeveloper = new DeveloperDto(1L, "CD Projekt Red");
        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(service.updateDeveloper(ArgumentMatchers.any(DeveloperDto.class))).thenReturn(updatedDeveloper);

        controller.doPut(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        Mockito.verify(service).updateDeveloper(ArgumentMatchers.any(DeveloperDto.class));
        assertEquals(mapper.writeValueAsString(updatedDeveloper), writer.toString());
    }


    @Test
    void deleteDeveloper() {
        Long developerId = 1L;

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        controller.doDelete(request, response);

        Mockito.verify(service).deleteDeveloper(developerId);
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}