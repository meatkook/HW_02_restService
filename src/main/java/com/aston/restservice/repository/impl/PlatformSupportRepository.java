package com.aston.restservice.repository.impl;

import com.aston.restservice.util.Database;
import com.aston.restservice.entity.PlatformSupport;
import com.aston.restservice.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlatformSupportRepository implements Repository<PlatformSupport, Long> {
    private Connection connection;

    public PlatformSupportRepository() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Optional<PlatformSupport> findById(Long id) {
        String sql = "SELECT * FROM platform_support WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                PlatformSupport platformSupport = new PlatformSupport();
                platformSupport.setId(resultSet.getLong("id"));
                platformSupport.setGameId(resultSet.getLong("game_id"));
                platformSupport.setPlatformId(resultSet.getLong("platform_id"));
                return Optional.of(platformSupport);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Iterable<PlatformSupport> findAll() {
        List<PlatformSupport> platformSupports = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM platform_support")) {

            while (resultSet.next()) {
                PlatformSupport platformSupport = new PlatformSupport();
                platformSupport.setId(resultSet.getLong("id"));
                platformSupport.setGameId(resultSet.getLong("game_id"));
                platformSupport.setPlatformId(resultSet.getLong("platform_id"));
                platformSupports.add(platformSupport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platformSupports;
    }

    @Override
    public PlatformSupport save(PlatformSupport platformSupport) {
        String sql = "INSERT INTO platform_support (game_id, platform_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, platformSupport.getGameId());
            statement.setLong(2, platformSupport.getPlatformId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating platform support failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                platformSupport.setId(generatedKeys.getLong(1));
                return platformSupport;
            } else {
                throw new SQLException("Creating platform support failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PlatformSupport update(PlatformSupport platformSupport) {
        String updateSql = "UPDATE platform_support SET game_id = ?, platform_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            statement.setLong(1, platformSupport.getGameId());
            statement.setLong(2, platformSupport.getPlatformId());
            statement.setLong(3, platformSupport.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating platform support failed, no rows affected.");
            }
            return platformSupport;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM platform_support WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(Long gameId, Long platformId) {
        String sql = "DELETE FROM platform_support WHERE game_id = ? and platform_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.setLong(2, platformId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}