package repository;

import com.kokotov.hw_02_restservice.entity.Developer;
import com.kokotov.hw_02_restservice.entity.Game;
import com.kokotov.hw_02_restservice.repository.impl.GameRepository;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class GameRepositoryTest {
    private Connection connection;
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        connection = mock(Connection.class);
        gameRepository = Mockito.spy(new GameRepository());
        gameRepository.setConnection(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @ParameterizedTest
    @CsvSource(value = {"Test Game 1, 1", "Test Game 2, 2", "Test Game 3, 3"}, delimiter = ',')
    void saveTest(String name, Long developerId) throws SQLException {
        Game game = new Game();
        game.setName(name);
        Developer developer = new Developer(developerId, "Test Developer");
        game.setDeveloper(developer);
        game.setPlatforms(new ArrayList<>());

        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Game savedGame = gameRepository.save(game);

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(statement, times(1)).setString(1, name);
        verify(statement, times(1)).setLong(2, developerId);
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong(1);

        assertEquals(1L, savedGame.getId());
        assertEquals(name, savedGame.getName());
        assertEquals(developer, savedGame.getDeveloper());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, Test Game 1", "2, Test Game 2", "3, Test Game 3"}, delimiter = ',')
    void updateTest(Long id, String name) throws SQLException {
        Game game = new Game(id, name, new Developer(), new ArrayList<>());

        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        Game updatedGame = gameRepository.update(game);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setString(1, name);
        verify(statement, times(1)).setLong(2, id);
        verify(statement, times(1)).executeUpdate();

        assertEquals(game, updatedGame);
    }

    @Test
    void deleteByIdTest() throws SQLException {
        long id = 1L;

        PreparedStatement statement = mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        gameRepository.deleteById(id);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setLong(1, id);
        verify(statement, times(1)).executeUpdate();
    }
}