package service;

import com.kokotov.hw_02_restservice.service.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AppConfigTest {
    private AppConfig appConfig;

    @BeforeEach
    public void setUp() {
        Properties properties = new Properties();
        InputStream configFile = new ByteArrayInputStream(("""
                url=jdbc:mysql://localhost:3306/test
                dbName=testDB
                username=user1
                password=pass123
                dbDriverClass=com.mysql.jdbc.Driver""").getBytes());
        appConfig = new AppConfig(
                properties,
                configFile,
                "url",
                "dbName",
                "username",
                "password",
                "dbDriverClass");
    }

    @Test
    public void testGetUrl() {
        assertEquals("jdbc:mysql://localhost:3306/test", appConfig.getUrl());
    }

    @Test
    public void testGetDbName() {
        assertEquals("testDB", appConfig.getDbName());
    }

    @Test
    public void testGetDbDriverClass() {
        assertEquals("com.mysql.jdbc.Driver", appConfig.getDbDriverClass());
    }

    @Test
    public void testGetUsername() {
        assertEquals("user1", appConfig.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("pass123", appConfig.getPassword());
    }
}