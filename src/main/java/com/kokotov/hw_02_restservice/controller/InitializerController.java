package com.kokotov.hw_02_restservice.controller;

import com.kokotov.hw_02_restservice.db.Database;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.SQLException;

@WebListener
public class InitializerController implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        try {
            Database.init();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
