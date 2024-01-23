package com.aston.restservice.util;

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

    /**
     * The database name for accessing the application.
     */
    private String dbName;

    /**
     * The username for accessing the application.
     */
    private String username;

    /**
     * The password for accessing the application.
     */
    private String password;

    /**
     * The database driver class name.
     */
    private String dbDriverClass;

    /**
     * Constructs a new AppConfig object and initializes its properties from the application.yml file.
     */
    public AppConfig() {
        try {
            Properties properties = new Properties();
            InputStream configFile = getClass().getClassLoader().getResourceAsStream("application.yml");
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

    public AppConfig(Properties properties,
                     InputStream configFile,
                     String urlProperty,
                     String dbNameProperty,
                     String usernameProperty,
                     String passwordProperty,
                     String dbDriverClassProperty) {

        try {
            properties.load(configFile);
            url = properties.getProperty(urlProperty);
            dbName = properties.getProperty(dbNameProperty);
            username = properties.getProperty(usernameProperty);
            password = properties.getProperty(passwordProperty);
            dbDriverClass = properties.getProperty(dbDriverClassProperty);

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

    /**
     * @return the database name
     */
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

    /**
     * @return the database driver class name
     */
    public String getDbDriverClass() {
        return dbDriverClass;
    }
}