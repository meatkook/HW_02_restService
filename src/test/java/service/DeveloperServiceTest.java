package service;

import com.kokotov.hw_02_restservice.dto.DeveloperDto;
import com.kokotov.hw_02_restservice.entity.Developer;
import com.kokotov.hw_02_restservice.mapper.impl.DeveloperMapper;
import com.kokotov.hw_02_restservice.repository.impl.DeveloperRepository;
import com.kokotov.hw_02_restservice.service.DeveloperService;
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
public class DeveloperServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private DeveloperMapper developerMapper;

    @InjectMocks
    private DeveloperService developerService;

    @BeforeEach
    void init() {
        developerService = new DeveloperService(developerRepository, developerMapper);
    }

    @Test
    void testGetDeveloperById() {
        Long id = 1L;
        Developer developer = new Developer();
        DeveloperDto developerDto = new DeveloperDto();
        when(developerRepository.findById(id)).thenReturn(Optional.of(developer));
        when(developerMapper.toDto(developer)).thenReturn(developerDto);

        DeveloperDto result = developerService.getDeveloperById(id);

        assertEquals(developerDto, result);
    }

    @Test
    void testCreateDeveloper() {
        DeveloperDto developerDto = new DeveloperDto();
        Developer developer = new Developer();
        when(developerMapper.toEntity(developerDto)).thenReturn(developer);
        when(developerRepository.save(developer)).thenReturn(developer);
        when(developerMapper.toDto(developer)).thenReturn(developerDto);

        DeveloperDto result = developerService.createDeveloper(developerDto);

        assertEquals(developerDto, result);
    }

    @Test
    void testUpdateDeveloper() {
        DeveloperDto developerDto = new DeveloperDto();
        Developer developer = new Developer();
        when(developerMapper.toEntity(developerDto)).thenReturn(developer);
        when(developerRepository.update(developer)).thenReturn(developer);
        when(developerMapper.toDto(developer)).thenReturn(developerDto);

        DeveloperDto result = developerService.updateDeveloper(developerDto);

        assertEquals(developerDto, result);
    }

    @Test
    void testDeleteDeveloper() {
        Long id = 1L;
        developerService.deleteDeveloper(id);
        verify(developerRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAllDevelopers() {
        Developer developer = new Developer();
        DeveloperDto developerDto = new DeveloperDto();
        when(developerMapper.toDto(developer)).thenReturn(developerDto);
        when(developerRepository.findAll()).thenReturn(Arrays.asList(developer, developer, developer));

        List<DeveloperDto> result = developerService.getAllDevelopers();

        assertEquals(3, result.size());
        assertEquals(developerDto, result.get(0));
        assertEquals(developerDto, result.get(1));
        assertEquals(developerDto, result.get(2));
    }
}