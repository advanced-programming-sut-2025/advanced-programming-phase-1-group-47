package com.StardewValley.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.badlogic.gdx.Gdx;

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

    public static boolean resetDatabase() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            
            // Delete the existing database file
            java.io.File dbFile = new java.io.File(DB_NAME);
            if (dbFile.exists()) {
                if (dbFile.delete()) {
                    Gdx.app.log("Database", "Existing database file deleted successfully");
                } else {
                    Gdx.app.error("Database", "Failed to delete existing database file");
                    return false;
                }
            }
            
            // Reinitialize the database
            return init();
        } catch (Exception e) {
            Gdx.app.error("Database", "Error resetting database", e);
            return false;
        }
    }

    private static void createTables() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
            "username TEXT NOT NULL, " +
            "password TEXT NOT NULL, " +
            "email TEXT, " +
            "gender TEXT, " +
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
            Gdx.app.log("Database", "Users table created/verified successfully");
            
            // Check if the table has the expected structure and migrate if needed
            try (Statement checkStmt = connection.createStatement()) {
                checkStmt.execute("SELECT username, password, email, gender, question_security_question, answer_security_question FROM users LIMIT 1");
                Gdx.app.log("Database", "Table structure verified - all required columns exist");
            } catch (SQLException e) {
                Gdx.app.log("Database", "Table structure check failed - attempting to add missing columns");
                migrateTable();
            }
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error creating tables", e);
        }
    }

    private static void migrateTable() {
        try (Statement stmt = connection.createStatement()) {
            // Add missing columns if they don't exist
            try {
                stmt.execute("ALTER TABLE users ADD COLUMN email TEXT");
                Gdx.app.log("Database", "Added email column");
            } catch (SQLException e) {
                Gdx.app.log("Database", "Email column already exists or couldn't be added");
            }
            
            try {
                stmt.execute("ALTER TABLE users ADD COLUMN gender TEXT");
                Gdx.app.log("Database", "Added gender column");
            } catch (SQLException e) {
                Gdx.app.log("Database", "Gender column already exists or couldn't be added");
            }
            
            Gdx.app.log("Database", "Table migration completed");
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error during table migration", e);
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
