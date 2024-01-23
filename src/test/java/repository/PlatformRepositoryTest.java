package repository;

import com.aston.restservice.entity.Platform;
import com.aston.restservice.repository.impl.PlatformRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

class PlatformRepositoryTest {
    private Connection connection;
    private PlatformRepository platformRepository;

    @BeforeEach
    void setUp() {
        connection = Mockito.mock(Connection.class);
        platformRepository = Mockito.spy(new PlatformRepository());
        platformRepository.setConnection(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void findById() throws SQLException {
        Long id = 1L;
        String name = "Platform 1";
        Platform platform = new Platform(id, "Platform 1");
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getString("name")).thenReturn(name);

        Optional<Platform> optionalPlatform = platformRepository.findById(id);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setLong(1, id);
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong("id");
        verify(resultSet, times(1)).getString("name");

        assertTrue(optionalPlatform.isPresent());
        assertEquals(platform, optionalPlatform.get());
    }

    @ParameterizedTest
    @CsvSource(value = {"Platform 1", "Platform 2", "Platform 3"})
    void saveTest(String name) throws SQLException {
        Platform platform = new Platform();
        platform.setName(name);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Platform savedPlatform = platformRepository.save(platform);

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(statement, times(1)).setString(1, name);
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong(1);

        assertEquals(1L, savedPlatform.getId());
        assertEquals(name, savedPlatform.getName());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, Platform 1", "2, Platform 2", "3, Platform 3"}, delimiter = ',')
    void updateTest(Long id, String name) throws SQLException {
        Platform platform = new Platform(id, name);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        Platform updatedPlatform = platformRepository.update(platform);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setString(1, name);
        verify(statement, times(1)).setLong(2, id);
        verify(statement, times(1)).executeUpdate();

        assertEquals(platform, updatedPlatform);
        platformRepository.deleteById(id);
    }

    @Test
    void findAllTest() throws SQLException {
        List<Platform> platforms = new ArrayList<>();
        platforms.add(new Platform(1L, "Platform 1"));
        platforms.add(new Platform(2L, "Platform 2"));
        platforms.add(new Platform(3L, "Platform 3"));

        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L, 3L);
        when(resultSet.getString("name")).thenReturn("Platform 1", "Platform 2", "Platform 3");

        Iterable<Platform> result = platformRepository.findAll();

        verify(statement, times(1)).executeQuery(anyString());

        assertIterableEquals(platforms, result);
    }

    @Test
    void deleteByIdTest() throws SQLException {
        long id = 1L;

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        platformRepository.deleteById(id);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setLong(1, id);
        verify(statement, times(1)).executeUpdate();
    }
}