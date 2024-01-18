package com.kokotov.hw_02_restservice.repository.impl;

import com.kokotov.hw_02_restservice.db.Database;
import com.kokotov.hw_02_restservice.entity.Developer;
import com.kokotov.hw_02_restservice.entity.Game;
import com.kokotov.hw_02_restservice.entity.Platform;
import com.kokotov.hw_02_restservice.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameRepository implements Repository<Game, Long> {
    private Connection connection;

    public GameRepository() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Optional<Game> findById(Long id) {
        String sql = "SELECT * FROM game WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Game game = buildGame(resultSet);
                return Optional.of(game);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Game buildGame(ResultSet resultSet) throws SQLException {
        Game game = new Game();
        game.setId(resultSet.getLong("id"));
        game.setName(resultSet.getString("name"));
        Long developerId = resultSet.getLong("developer_id");
        Optional<Developer> developer = DeveloperRepository.getDeveloper(developerId, connection);
        game.setDeveloper(developer.orElse(null));
        List<Platform> platforms = getPlatformsForGame(game.getId(), connection);
        game.setPlatforms(platforms);
        return game;
    }

    @Override
    public Iterable<Game> findAll() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM game ORDER BY id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Game game = buildGame(resultSet);
                games.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public Game save(Game game) {
        String sql = "INSERT INTO game (name, developer_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, game.getName());
            statement.setLong(2, game.getDeveloper().getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating game failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                game.setId(generatedKeys.getLong(1));
                savePlatformsForGame(game, connection);
                return game;
            } else {
                throw new SQLException("Creating game failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Game update(Game game) {
        String updateSql;
        Long developerId = null;
        String gameName = game.getName();
        if (game.getDeveloper() != null) {
            developerId = game.getDeveloper().getId();
        }
        if (developerId == null){
            updateSql = "UPDATE game SET name = ?  WHERE id = ?";
        }
        else if (gameName == null){
            updateSql = "UPDATE game SET developer_id = ? WHERE id = ?";
        }
        else {
            updateSql = "UPDATE game SET name = ?, developer_id = ? WHERE id = ?";
        }
        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {

            if(developerId == null){
                statement.setString(1, gameName);
                statement.setLong(2, game.getId());
            }
            else if (gameName == null){
                statement.setLong(1, developerId);
                statement.setLong(2, game.getId());
            }
            else {
                statement.setString(1, gameName);
                statement.setLong(2, developerId);
                statement.setLong(3, game.getId());
            }

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating game failed, no rows affected.");
            }
            return game;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM game WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Platform> getPlatformsForGame(Long gameId, Connection connection) {
        List<Platform> platforms = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT platform_id FROM platform_support WHERE game_id = ?");
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long platformId = resultSet.getLong("platform_id");
                Optional<Platform> platform = PlatformRepository.getPlatform(platformId, connection);
                platform.ifPresent(platforms::add);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platforms;
    }

    private void savePlatformsForGame(Game game, Connection connection) {
        try {
            for (Platform platform : game.getPlatforms()) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO platform_support (game_id, platform_id) VALUES (?, ?)");
                statement.setLong(1, game.getId());
                statement.setLong(2, platform.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
