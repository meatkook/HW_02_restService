package service;

import com.aston.restservice.dto.PlatformDto;
import com.aston.restservice.entity.Platform;
import com.aston.restservice.mapper.impl.PlatformMapper;
import com.aston.restservice.repository.impl.PlatformRepository;
import com.aston.restservice.service.PlatformService;
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
public class PlatformServiceTest {

    @Mock
    private PlatformRepository platformRepository;

    @Mock
    private PlatformMapper platformMapper;

    @InjectMocks
    private PlatformService platformService;

    @BeforeEach
    void init() {
        platformService = new PlatformService(platformRepository, platformMapper);
    }

    @Test
    void testGetPlatformById() {
        Long id = 1L;
        Platform platform = new Platform();
        PlatformDto platformDto = new PlatformDto();
        when(platformRepository.findById(id)).thenReturn(Optional.of(platform));
        when(platformMapper.toDto(platform)).thenReturn(platformDto);

        PlatformDto result = platformService.getPlatformById(id);

        assertEquals(platformDto, result);
    }

    @Test
    void testCreatePlatform() {
        PlatformDto platformDto = new PlatformDto();
        Platform platform = new Platform();
        when(platformMapper.toEntity(platformDto)).thenReturn(platform);
        when(platformRepository.save(platform)).thenReturn(platform);
        when(platformMapper.toDto(platform)).thenReturn(platformDto);

        PlatformDto result = platformService.createPlatform(platformDto);

        assertEquals(platformDto, result);
    }

    @Test
    void testUpdatePlatform() {
        PlatformDto platformDto = new PlatformDto();
        Platform platform = new Platform();
        when(platformMapper.toEntity(platformDto)).thenReturn(platform);
        when(platformRepository.update(platform)).thenReturn(platform);
        when(platformMapper.toDto(platform)).thenReturn(platformDto);

        PlatformDto result = platformService.updatePlatform(platformDto);

        assertEquals(platformDto, result);
    }

    @Test
    void testDeletePlatform() {
        Long id = 1L;
        platformService.deletePlatform(id);
        verify(platformRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAll() {
        Platform platform = new Platform();
        PlatformDto platformDto = new PlatformDto();
        when(platformMapper.toDto(platform)).thenReturn(platformDto);
        when(platformRepository.findAll()).thenReturn(Arrays.asList(platform, platform, platform));

        List<PlatformDto> result = platformService.getAll();

        assertEquals(3, result.size());
        assertEquals(platformDto, result.get(0));
        assertEquals(platformDto, result.get(1));
        assertEquals(platformDto, result.get(2));
    }
}