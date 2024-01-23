package com.aston.restservice.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final HikariDataSource dataSource;

    static {
        HikariConfig hikariConfig = new HikariConfig();
        AppConfig appConfig = new AppConfig();
        try {
            Class.forName(appConfig.getDbDriverClass());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        hikariConfig.setJdbcUrl(appConfig.getUrl());
        hikariConfig.setUsername(appConfig.getUsername());
        hikariConfig.setPassword(appConfig.getPassword());
        hikariConfig.setDriverClassName(appConfig.getDbDriverClass());

        dataSource = new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void init() throws SQLException {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()) {
            StringBuilder query = new StringBuilder();
            try (InputStream is = Database.class.getClassLoader().getResourceAsStream("init.sql")) {
                assert is != null;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        query.append(line).append("\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            statement.execute(query.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}