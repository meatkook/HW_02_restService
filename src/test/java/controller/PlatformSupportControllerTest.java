package controller;

import com.kokotov.hw_02_restservice.controller.PlatformSupportController;
import com.kokotov.hw_02_restservice.dto.PlatformSupportDto;
import com.kokotov.hw_02_restservice.service.PlatformSupportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

class PlatformSupportControllerTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private PlatformSupportController controller;
    private PlatformSupportService service;

    @BeforeEach
    void setMocks() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        service = Mockito.mock(PlatformSupportService.class);
        controller = new PlatformSupportController(service);
    }

    @Test
    void addPlatformSupport() {
        Mockito.when(request.getPathInfo()).thenReturn("/1/2");

        controller.doPost(request, response);

        Mockito.verify(service).create(ArgumentMatchers.any(PlatformSupportDto.class));
        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
    }

    @Test
    void deletePlatformSupport() {
        Mockito.when(request.getPathInfo()).thenReturn("/1/2");

        controller.doDelete(request, response);

        Mockito.verify(service).delete(1L, 2L);
        Mockito.verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    void addPlatformSupportBadRequest() {
        Mockito.when(request.getPathInfo()).thenReturn("/1");

        controller.doPost(request, response);

        Mockito.verify(service, Mockito.never()).create(ArgumentMatchers.any(PlatformSupportDto.class));
        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void deletePlatformSupportBadRequest() {
        Mockito.when(request.getPathInfo()).thenReturn("/1");

        controller.doDelete(request, response);

        Mockito.verify(service, Mockito.never()).delete(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}