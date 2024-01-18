package service;

import com.kokotov.hw_02_restservice.dto.PlatformSupportDto;
import com.kokotov.hw_02_restservice.entity.PlatformSupport;
import com.kokotov.hw_02_restservice.mapper.impl.PlatformSupportMapper;
import com.kokotov.hw_02_restservice.repository.impl.PlatformSupportRepository;
import com.kokotov.hw_02_restservice.service.PlatformSupportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlatformSupportServiceTest {

    @Mock
    private PlatformSupportRepository platformSupportRepository;

    @Mock
    private PlatformSupportMapper platformSupportMapper;

    @InjectMocks
    private PlatformSupportService platformSupportService;

    @BeforeEach
    void init() {
        platformSupportService = new PlatformSupportService(platformSupportRepository, platformSupportMapper);
    }

    @Test
    void testCreate() {
        PlatformSupportDto supportDto = new PlatformSupportDto();
        PlatformSupport support = new PlatformSupport();
        when(platformSupportMapper.toEntity(supportDto)).thenReturn(support);

        platformSupportService.create(supportDto);

        verify(platformSupportRepository, times(1)).save(support);
    }

    @Test
    void testDelete() {
        Long gameId = 1L;
        Long platformId = 1L;

        platformSupportService.delete(gameId, platformId);

        verify(platformSupportRepository, times(1)).deleteById(gameId, platformId);
    }
}