package com.kokotov.hw_02_restservice.repository.impl;

import com.kokotov.hw_02_restservice.db.Database;
import com.kokotov.hw_02_restservice.entity.Developer;
import com.kokotov.hw_02_restservice.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperRepository implements Repository<Developer, Long> {

    private Connection connection;

    public DeveloperRepository(){
        try {
            connection = Database.getConnection();
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }

    }

    @Override
    public Optional<Developer> findById(Long id) {
        return getDeveloper(id, connection);
    }

    public static Optional<Developer> getDeveloper(Long id, Connection connection) {
        String sql = "SELECT * FROM developer WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Developer developer = new Developer();
                developer.setId(resultSet.getLong("id"));
                developer.setName(resultSet.getString("developer_name"));
                return Optional.of(developer);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Developer> findAll() {
        String sql = "SELECT * FROM developer";
        List<Developer> developers = new ArrayList<>();
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery (sql)) {

            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setId(resultSet.getLong("id"));
                developer.setName(resultSet.getString("developer_name"));
                developers.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    @Override
    public Developer save(Developer developer) {
        String sql = "INSERT INTO developer (developer_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, developer.getName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating developer failed, no rows affected.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                developer.setId(generatedKeys.getLong(1));
                return developer;
            } else {
                throw new SQLException("Creating developer failed, no ID obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Developer update(Developer developer) {
        String updateSql = "UPDATE developer SET developer_name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
            statement.setString(1, developer.getName());
            statement.setLong(2, developer.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating developer failed, no rows affected.");
            }
            return developer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM developer WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}