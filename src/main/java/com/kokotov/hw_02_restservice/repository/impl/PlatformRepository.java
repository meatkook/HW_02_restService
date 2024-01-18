package com.kokotov.hw_02_restservice.repository.impl;

import com.kokotov.hw_02_restservice.db.Database;
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

public class PlatformRepository implements Repository<Platform, Long> {

    private Connection connection;

    public PlatformRepository() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Optional<Platform> findById(Long id) {
        return getPlatform(id, connection);
    }

    public static Optional<Platform> getPlatform(Long id, Connection connection){
        String sql = "SELECT * FROM platform WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Platform platform = new Platform();
                platform.setId(resultSet.getLong("id"));
                platform.setName(resultSet.getString("name"));
                return Optional.of(platform);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Platform> findAll() {
        List<Platform> platforms = new ArrayList<>();
        String sql = "SELECT * FROM platform";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Platform platform = new Platform();
                platform.setId(resultSet.getLong("id"));
                platform.setName(resultSet.getString("name"));
                platforms.add(platform);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platforms;
    }

    @Override
    public Platform save(Platform platform) {
        String sql = "INSERT INTO platform (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, platform.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating platform failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                platform.setId(generatedKeys.getLong(1));
                return platform;
            } else {
                throw new SQLException("Creating platform failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Platform update(Platform platform) {
        String updateSql = "UPDATE platform SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            statement.setString(1, platform.getName());
            statement.setLong(2, platform.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating platform failed, no rows affected.");
            }
            return platform;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        String deletePlatformSql = "DELETE FROM platform WHERE id = ?";
        try  {
            try (PreparedStatement deletePlatformStatement = connection.prepareStatement(deletePlatformSql)) {
                deletePlatformStatement.setLong(1, id);
                deletePlatformStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}