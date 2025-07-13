package com.StardewValley.DataBase;

import com.badlogic.gdx.Gdx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInit {
    private static final String DB_NAME = "users.db";
    private static Connection connection;

    public static boolean init() {
        try {
            String url = "jdbc:sqlite:" + DB_NAME;
            connection = DriverManager.getConnection(url);

            if (connection != null) {
                createTables();
                Gdx.app.log("Database", "Connection established and tables created.");
                return true;
            }
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error initializing database", e);
        }
        return false;
    }

    private static void createTables() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
            "username TEXT NOT NULL, " +
            "password TEXT NOT NULL, " +
            "question_security_question TEXT NOT NULL, " +
            "answer_security_question TEXT NOT NULL, " +
            "avatar TEXT," +
            "last_game_path TEXT, " +
            "score INTEGER DEFAULT 0, " +
            "survival_time INTEGER DEFAULT 0, " +
            "kill_count INTEGER DEFAULT 0, " +
            "auto_reload BOOLEAN DEFAULT 0" +
            ")";


        try (Statement stmt = connection.createStatement()) {
            stmt.execute(usersTable);
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error creating tables", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Gdx.app.log("Database", "Connection closed.");
            }
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error closing database connection", e);
        }
    }
}
