package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.aston.restservice.controller.PlatformController;
import com.aston.restservice.dto.PlatformDto;
import com.aston.restservice.service.PlatformService;
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
class PlatformControllerTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PlatformController controller;
    private PlatformService service;
    private ObjectMapper mapper;

    @BeforeEach
    void setMocks() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        service = Mockito.mock(PlatformService.class);
        controller = new PlatformController(service);
        mapper = new ObjectMapper();
    }

    @Test
    void getAllPlatforms() throws IOException {
        List<PlatformDto> platforms = new ArrayList<>();
        PlatformDto platform1 = new PlatformDto(1L, "PlayStation");
        PlatformDto platform2 = new PlatformDto(2L, "Xbox");
        platforms.add(platform1);
        platforms.add(platform2);
        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/");
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(service.getAll()).thenReturn(platforms);

        controller.doGet(request, response);
        printWriter.flush();
        assertEquals(mapper.writeValueAsString(platforms), writer.toString());
    }

    @Test
    void getPlatformById() throws IOException {
        PlatformDto platform = new PlatformDto();
        platform.setId(1L);
        platform.setName("PlayStation");
        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(service.getPlatformById(1L)).thenReturn(platform);

        controller.doGet(request, response);
        printWriter.flush();
        assertEquals(mapper.writeValueAsString(platform), writer.toString());
    }

    @Test
    void addPlatform() throws IOException {
        String json = "{\"id\":1,\"name\":\"PlayStation\"}";
        PlatformDto dto = new PlatformDto(1L, "PlayStation");
        StringWriter writer = new StringWriter();

        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(service.createPlatform(ArgumentMatchers.any(PlatformDto.class))).thenReturn(dto);

        controller.doPost(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
        Mockito.verify(service).createPlatform(ArgumentMatchers.any(PlatformDto.class));
        assertEquals(mapper.writeValueAsString(dto), writer.toString());
    }

    @Test
    void updatePlatform() throws IOException {
        String json = "{\"id\":1,\"name\":\"PlayStation\"}";
        PlatformDto updatedPlatform = new PlatformDto(1L, "PlayStation");
        StringWriter writer = new StringWriter();

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        PrintWriter printWriter = new PrintWriter(writer);
        Mockito.when(response.getWriter()).thenReturn(printWriter);
        Mockito.when(service.updatePlatform(ArgumentMatchers.any(PlatformDto.class))).thenReturn(updatedPlatform);

        controller.doPut(request, response);

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        Mockito.verify(service).updatePlatform(ArgumentMatchers.any(PlatformDto.class));
        assertEquals(mapper.writeValueAsString(updatedPlatform), writer.toString());
    }

    @Test
    void deletePlatform() {
        Long platformId = 1L;

        Mockito.when(request.getPathInfo()).thenReturn("/1");
        controller.doDelete(request, response);

        Mockito.verify(service).deletePlatform(platformId);
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }
}