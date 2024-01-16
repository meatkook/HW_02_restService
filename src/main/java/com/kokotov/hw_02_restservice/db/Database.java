package com.kokotov.hw_02_restservice.db;

import com.kokotov.hw_02_restservice.service.AppConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final HikariDataSource dataSource;
    private static final String dbName;

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
        dbName = appConfig.getDbName();
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void init() throws SQLException {
        Connection connection = getConnection();
        try (Statement statement = connection.createStatement()){

            statement.execute("CREATE DATABASE " + dbName);
        }
        catch (SQLException e){
            System.out.println(dbName + " is already exist");
        }

        try (Statement statement = connection.createStatement()) {

            String createDeveloperTable = """
                        CREATE TABLE IF NOT EXISTS developer (
                        id SERIAL PRIMARY KEY,
                        developer_name VARCHAR(100)
                    )
                    """;
            statement.executeUpdate(createDeveloperTable);

            String sqlDevelopersCount = "SELECT COUNT(*) FROM developer";
            ResultSet resultSet = statement.executeQuery(sqlDevelopersCount);
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count == 0){
                String addDeveloperData = """
                    INSERT INTO developer (developer_name)
                    VALUES
                    ('CD Projekt Red'),
                    ('Naughty Dog'),
                    ('Rockstar Games'),
                    ('Ubisoft'),
                    ('Bethesda Game Studios'),
                    ('Nintendo'),
                    ('Square Enix'),
                    ('Capcom'),
                    ('Blizzard Entertainment'),
                    ('Epic Games');""";
                statement.execute(addDeveloperData);
            }

            String createGameTable = """
                    CREATE TABLE IF NOT EXISTS game (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100),
                    developer_id INT,
                    FOREIGN KEY (developer_id) REFERENCES developer(id) ON DELETE CASCADE
                    )""";
            statement.executeUpdate(createGameTable);

            String sqlGameCount = "SELECT COUNT(*) FROM game";
            resultSet = statement.executeQuery(sqlGameCount);
            resultSet.next();
            count = resultSet.getInt(1);
            if(count == 0){
                String addAccountsData = """
                    INSERT INTO game (name, developer_id)
                    VALUES
                    ('The Witcher 3: Wild Hunt', 1),
                    ('Cyberpunk 2077', 1),
                    ('Gwent: The Witcher Card Game', 1),
                    ('The Last of Us Part II', 2),
                    ('Uncharted 4: A Thief''s End', 2),
                    ('Uncharted: The Lost Legacy', 2),
                    ('Grand Theft Auto V', 3),
                    ('Red Dead Redemption 2', 3),
                    ('Red Dead Redemption', 3),
                    ('Assassin''s Creed Valhalla', 4),
                    ('Assassin''s Creed Odyssey', 4),
                    ('Far Cry 5', 4),
                    ('The Elder Scrolls V: Skyrim', 5),
                    ('The Elder Scrolls Online', 5),
                    ('Fallout 4', 5),
                    ('The Legend of Zelda: Breath of the Wild', 6),
                    ('The Legend of Zelda: Ocarina of Time', 6),
                    ('Mario Kart 8 Deluxe', 6),
                    ('Final Fantasy XV', 7),
                    ('Final Fantasy VII Remake', 7),
                    ('Kingdom Hearts III', 7),
                    ('Resident Evil Village', 8),
                    ('Street Fighter V', 8),
                    ('Devil May Cry 5', 8),
                    ('Overwatch', 9),
                    ('Diablo III', 9),
                    ('World of Warcraft', 9),
                    ('Fortnite', 10),
                    ('Fortnite: Save the World', 10),
                    ('Infinity Blade', 10);
                    """;
                statement.execute(addAccountsData);
            }

            String createPlarformTable = """
                    CREATE TABLE IF NOT EXISTS platform (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100)
                    )
                    """;
            statement.executeUpdate(createPlarformTable);

            String sqlPlatformCount = "SELECT COUNT(*) FROM platform";
            resultSet = statement.executeQuery(sqlPlatformCount);
            resultSet.next();
            count = resultSet.getInt(1);
            if(count == 0){
                String addPlatformData = """
                    INSERT INTO platform (name)
                    VALUES
                    ('PC'),
                    ('PlayStation 4'),
                    ('PlayStation 5'),
                    ('Xbox One'),
                    ('Xbox Series X'),
                    ('PlayStation Vita'),
                    ('Nintendo Switch');""";
                statement.execute(addPlatformData);
            }

            String createTypesTable = """
                    CREATE TABLE IF NOT EXISTS platform_support (
                    id SERIAL PRIMARY KEY,
                    game_id INT,
                    platform_id INT,
                    FOREIGN KEY (platform_id) REFERENCES platform(id) ON DELETE CASCADE,
                    FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE,
                    UNIQUE(game_id, platform_id)
                    )
                    """;
            statement.executeUpdate(createTypesTable);

            String sqlSupportCount = "SELECT COUNT(*) FROM platform_support";
            resultSet = statement.executeQuery(sqlSupportCount);
            resultSet.next();
            count = resultSet.getInt(1);
            if(count == 0){
                String addTypesData = """
                    INSERT INTO platform_support (game_id, platform_id)
                    VALUES
                    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), -- The Witcher 3: Wild Hunt
                    (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), -- Cyberpunk 2077
                    (3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 7), -- Gwent: The Witcher Card Game
                    (4, 2), (4, 3), (4, 4), -- The Last of Us Part II
                    (5, 2), (5, 3), (5, 4), -- Uncharted 4: A Thief's End
                    (6, 2), (6, 3), (6, 4), -- Uncharted: The Lost Legacy
                    (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), -- Grand Theft Auto V
                    (8, 1), (8, 2), (8, 3), (8, 4), (8, 5), -- Red Dead Redemption 2
                    (9, 1), (9, 2), (9, 3), (9, 4), -- Red Dead Redemption
                    (10, 1), (10, 2), (10, 3), (10, 4), (10, 5), -- Assassin's Creed Valhalla
                    (11, 1), (11, 2), (11, 3), (11, 4), -- Assassin's Creed Odyssey
                    (12, 1), (12, 2), (12, 3), (12, 4), -- Far Cry 5
                    (13, 1), (13, 2), (13, 3), -- The Elder Scrolls V: Skyrim
                    (14, 1), (14, 2), (14, 3), -- The Elder Scrolls Online
                    (15, 1), (15, 2), (15, 3), -- Fallout 4
                    (16, 7), (16, 6), -- The Legend of Zelda: Breath of the Wild
                    (17, 7), (17, 6), -- The Legend of Zelda: Ocarina of Time
                    (18, 7), (18, 6), -- Mario Kart 8 Deluxe
                    (19, 1), (19, 2), (19, 3), (19, 4), -- Final Fantasy XV
                    (20, 1), (20, 2), (20, 3), (20, 4), -- Final Fantasy VII Remake
                    (21, 1), (21, 2), (21, 3), (21, 4), -- Kingdom Hearts III
                    (22, 1), (22, 2), (22, 3), -- Resident Evil Village
                    (23, 1), (23, 2), (23, 3), -- Street Fighter V
                    (24, 1), (24, 2), (24, 3), -- Devil May Cry 5
                    (25, 1), (25, 2), (25, 3), -- Overwatch
                    (26, 1), (26, 2), (26, 3), -- Diablo III
                    (27, 1), (27, 2), (27, 3), -- World of Warcraft
                    (28, 1), (28, 2), (28, 3), (28, 4), (28, 5), -- Fortnite
                    (29, 1), (29, 2), -- Fortnite: Save the World
                    (30, 1); -- Infinity Blade
                    """;
                statement.execute(addTypesData);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}