package com.kokotov.hw_02_restservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class represents the configuration for the application.
 * It contains properties such as the URL, full URL, username, password, and database name.
 */
public class AppConfig {
    /**
     * The URL of the application.
     */
    private String url;

    private String dbName;

    /**
     * The username for accessing the application.
     */
    private String username;

    /**
     * The password for accessing the application.
     */
    private String password;

    private String dbDriverClass;

    /**
     * Constructs a new AppConfig object and initializes its properties from the application.yml file.
     */
    public AppConfig() {
        try {
            Properties properties = new Properties();
            InputStream configFile = getClass().getClassLoader().getResourceAsStream("application.yml");
//            FileInputStream configFile = new FileInputStream("src/main/resources/application.yml");
            properties.load(configFile);

            url = properties.getProperty("url");
            dbName = properties.getProperty("dbName");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            dbDriverClass = properties.getProperty("dbDriverClass");

            assert configFile != null;
            configFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the full URL of the database
     */
    public String getUrl() {
        return url;
    }

    public String getDbName() {
        return dbName;
    }

    /**
     * @return the username for accessing the database
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password for accessing the database
     */
    public String getPassword() {
        return password;
    }

    public String getDbDriverClass() {
        return dbDriverClass;
    }
}

