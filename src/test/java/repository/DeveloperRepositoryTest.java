package repository;

import com.kokotov.hw_02_restservice.entity.Developer;
import com.kokotov.hw_02_restservice.repository.impl.DeveloperRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeveloperRepositoryTest {
    private Connection connection;
    private DeveloperRepository developerRepository;

    @BeforeEach
    void setUp() {
        connection = Mockito.mock(Connection.class);
        developerRepository = Mockito.spy(new DeveloperRepository());
        developerRepository.setConnection(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @ParameterizedTest
    @CsvSource(value = {"1, Developer", "2, Developer 2", "3, Developer 3"}, delimiter = ',')
    void findById(Long id, String name) throws SQLException {
        Developer developer = new Developer(id, name);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong("id")).thenReturn(id);
        when(resultSet.getString("developer_name")).thenReturn(name);

        Optional<Developer> optionalDeveloper = developerRepository.findById(id);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setLong(1, id);
        verify(statement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong("id");
        verify(resultSet, times(1)).getString("developer_name");

        assertTrue(optionalDeveloper.isPresent());
        assertEquals(developer, optionalDeveloper.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Developer","Developer 2","Developer 3"})
    void saveTest(String name) throws SQLException {
        Developer developer = new Developer();
        developer.setName(name);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getLong(1)).thenReturn(1L);

        Developer savedDeveloper = developerRepository.save(developer);

        verify(connection, times(1)).prepareStatement(anyString(), anyInt());
        verify(statement, times(1)).setString(1, name);
        verify(statement, times(1)).executeUpdate();
        verify(statement, times(1)).getGeneratedKeys();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(1)).getLong(1);

        assertEquals(1L, savedDeveloper.getId());
        assertEquals(name, savedDeveloper.getName());
    }

    @ParameterizedTest
    @CsvSource(value = {"1, Developer", "2, Developer 2", "3, Developer 3"}, delimiter = ',')
    void updateTest(Long id, String name) throws SQLException {
        Developer developer = new Developer(id, name);

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        Developer updatedDeveloper = developerRepository.update(developer);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setString(1, name);
        verify(statement, times(1)).setLong(2, id);
        verify(statement, times(1)).executeUpdate();

        assertEquals(developer, updatedDeveloper);
        developerRepository.deleteById(id);
    }

    @Test
    void findAllTest() throws SQLException {
        List<Developer> developers = new ArrayList<>();
        developers.add(new Developer(1L, "Alice"));
        developers.add(new Developer(2L, "Bob"));
        developers.add(new Developer(3L, "Charlie"));

        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, true, false);
        when(resultSet.getLong("id")).thenReturn(1L, 2L, 3L);
        when(resultSet.getString("developer_name")).thenReturn("Alice", "Bob", "Charlie");


        Iterable<Developer> result = developerRepository.findAll();

        verify(statement, times(1)).executeQuery(anyString());

        assertIterableEquals(developers, result);
    }

    @Test
    void deleteByIdTest() throws SQLException {
        long id = 1L;

        PreparedStatement statement = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);

        developerRepository.deleteById(id);

        verify(connection, times(1)).prepareStatement(anyString());
        verify(statement, times(1)).setLong(1, id);
        verify(statement, times(1)).executeUpdate();

        verify(statement).setLong(1, id);
        verify(statement).executeUpdate();
    }
}