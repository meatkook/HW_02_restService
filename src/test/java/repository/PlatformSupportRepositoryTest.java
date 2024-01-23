package repository;

import com.aston.restservice.entity.PlatformSupport;
import com.aston.restservice.repository.impl.PlatformSupportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlatformSupportRepositoryTest {
    private Connection connection;
    private PlatformSupportRepository platformSupportRepository;

    @BeforeEach
    void setUp() {
        connection = Mockito.mock(Connection.class);
        platformSupportRepository = Mockito.spy(new PlatformSupportRepository());
        platformSupportRepository.setConnection(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void findById() throws SQLException {
        Long id = 1L;
        Long gameId = 1L;
        Long platformId = 1L;
        PlatformSupport platformSupport = new PlatformSupport(id, gameId, platformId);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getLong("game_id")).thenReturn(gameId);
        when(resultSet.getLong("platform_id")).thenReturn(platformId);

        Optional<PlatformSupport> optionalPlatformSupport = platformSupportRepository.findById(id);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setLong(1, id);
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong("id");
        verify(resultSet, times(1)).getLong("game_id");
        verify(resultSet, times(1)).getLong("platform_id");

        assertTrue(optionalPlatformSupport.isPresent());
        assertEquals(platformSupport, optionalPlatformSupport.get());
    }

    @Test
    void findAllTest() throws SQLException {
        List<PlatformSupport> platformSupports = new ArrayList<>();
        platformSupports.add(new PlatformSupport(1L, 1L, 1L));
        platformSupports.add(new PlatformSupport(2L, 2L, 2L));
        platformSupports.add(new PlatformSupport(3L, 3L, 3L));

        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L, 3L);
        when(resultSet.getLong("game_id")).thenReturn(1L, 2L, 3L);
        when(resultSet.getLong("platform_id")).thenReturn(1L, 2L, 3L);

        Iterable<PlatformSupport> result = platformSupportRepository.findAll();

        verify(statement, times(1)).executeQuery(anyString());

        assertIterableEquals(platformSupports, result);
    }

    @Test
    void saveTest() throws SQLException {
        Long gameId = 1L;
        Long platformId = 1L;
        PlatformSupport platformSupport = new PlatformSupport();
        platformSupport.setGameId(gameId);
        platformSupport.setPlatformId(platformId);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        PlatformSupport savedPlatformSupport = platformSupportRepository.save(platformSupport);

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(statement, times(1)).setLong(1, gameId);
        verify(statement, times(1)).setLong(2, platformId);
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong(1);

        assertEquals(1L, savedPlatformSupport.getId());
        assertEquals(gameId, savedPlatformSupport.getGameId());
        assertEquals(platformId, savedPlatformSupport.getPlatformId());
    }

    @Test
    void updateTest() throws SQLException {
        long id = 1L;
        Long gameId = 1L;
        Long platformId = 1L;
        PlatformSupport platformSupport = new PlatformSupport(id, gameId, platformId);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        PlatformSupport updatedPlatformSupport = platformSupportRepository.update(platformSupport);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setLong(1, gameId);
        verify(statement, times(1)).setLong(2, platformId);
        verify(statement, times(1)).setLong(3, id);
        verify(statement, times(1)).executeUpdate();

        assertEquals(platformSupport, updatedPlatformSupport);
        platformSupportRepository.deleteById(gameId, platformId);
    }

    @Test
    void deleteByIdTest() throws SQLException {
        long gameId = 1L;
        long platformId = 1L;

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        platformSupportRepository.deleteById(gameId, platformId);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setLong(1, gameId);
        verify(statement, times(1)).setLong(2, platformId);
        verify(statement, times(1)).executeUpdate();
    }
}