-- CREATE DATABASE game_store;
DO $$
BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'game_store') THEN
            CREATE DATABASE game_store;
END IF;
END $$;

-- Создание таблицы developer
CREATE TABLE IF NOT EXISTS developer (
                                         id SERIAL PRIMARY KEY,
                                         developer_name VARCHAR(100)
    );

-- Заполнение таблицы developer, если она пуста
DO $$
    DECLARE
developersCount INT;
BEGIN
SELECT COUNT(*) INTO developersCount FROM developer;
IF developersCount = 0 THEN
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
                ('Epic Games');
END IF;
END $$;

-- Создание таблицы game
CREATE TABLE IF NOT EXISTS game (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(100),
    developer_id INT,
    FOREIGN KEY (developer_id) REFERENCES developer(id) ON DELETE CASCADE
    );

-- Заполнение таблицы game, если она пуста
DO $$
    DECLARE
gameCount INT;
BEGIN
SELECT COUNT(*) INTO gameCount FROM game;
IF gameCount = 0 THEN
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
END IF;
END $$;

-- Создание таблицы platform
CREATE TABLE IF NOT EXISTS platform (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(100)
    );

-- Заполнение таблицы platform, если она пуста
DO $$
    DECLARE
platformCount INT;
BEGIN
SELECT COUNT(*) INTO platformCount FROM platform;
IF platformCount = 0 THEN
            INSERT INTO platform (name)
            VALUES
                ('PC'),
                ('PlayStation 4'),
                ('PlayStation 5'),
                ('Xbox One'),
                ('Xbox Series X'),
                ('PlayStation Vita'),
                ('Nintendo Switch');
END IF;
END $$;

-- Создание таблицы platform_support
CREATE TABLE IF NOT EXISTS platform_support (
                                                id SERIAL PRIMARY KEY,
                                                game_id INT,
                                                platform_id INT,
                                                FOREIGN KEY (platform_id) REFERENCES platform(id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES game(id) ON DELETE CASCADE,
    UNIQUE(game_id, platform_id)
    );

-- Заполнение таблицы platform_support, если она пуста
DO $$
    DECLARE
supportCount INT;
BEGIN
SELECT COUNT(*) INTO supportCount FROM platform_support;
IF supportCount = 0 THEN
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
END IF;
END $$;