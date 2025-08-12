package com.StardewValley.DataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.StardewValley.model.User;
import com.badlogic.gdx.Gdx;

public class UserDBCommands {

    private void logSQL(String sql, List<Object> params) {
        StringBuilder sb = new StringBuilder("SQL: ").append(sql);
        if (params != null && !params.isEmpty()) {
            sb.append(" | Parameters: ");
            for (Object param : params) {
                sb.append(param).append(", ");
            }
            sb.setLength(sb.length() - 2); // حذف , آخر
        }
        Gdx.app.error("SQL", sb.toString());
    }

    public void saveUser(User user) throws SQLException {
        String sql = "INSERT INTO users (" +
            "username, password, email, gender, question_security_question, answer_security_question, avatar, " +
            "last_game_path, score, survival_time, kill_count, auto_reload" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object> params = Arrays.asList(
            user.getUsername(),
            user.getPassword(),
            user.getEmail() != null ? user.getEmail() : "",
            user.getGender() != null ? user.getGender().valueOf() : "",
            user.getSecurityQuestion(),
            user.getSecurityAnswer(),
            user.getEmail() != null ? user.getEmail() : "", // avatar field
            "-", // last_game_path
            user.getGamePlayed(),
            user.getGamePlayed(), // survival_time
            user.getGamePlayed(), // kill_count
            true // auto_reload
        );

        try (PreparedStatement pstmt = DataBaseInit.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            int rowsAffected = pstmt.executeUpdate();
            Gdx.app.log("Database", "User saved successfully. Rows affected: " + rowsAffected);
        }

        logSQL(sql, params);
    }

    public static User getUser(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (PreparedStatement pstmt = DataBaseInit.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    "", // nickname field (empty for now)
                    rs.getString("gender") != null ? com.StardewValley.model.enums.Gender.getGenderEnum(rs.getString("gender")) : null,
                    rs.getString("question_security_question"),
                    rs.getString("answer_security_question")
                );
                
                // Set additional fields if they exist
                try {
                    user.setGame_played(rs.getInt("score"));
                } catch (Exception e) {
                    // Field might not exist, ignore
                }
            }

            new UserDBCommands().logSQL(sql, Arrays.asList(username));
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error getting user", e);
        }

        return user;
    }

    public void updateUser(User user, String username) {
        String sql = "UPDATE users SET " +
            "username = ?, " +
            "password = ?, " +
            "email = ?, " +
            "gender = ?, " +
            "question_security_question = ?, " +
            "answer_security_question = ?, " +
            "avatar = ?, " +
            "score = ?, " +
            "survival_time = ?, " +
            "kill_count = ?, " +
            "auto_reload = ? " +
            "WHERE username = ?";

        List<Object> params = Arrays.asList(
            user.getUsername(),
            user.getPassword(),
            user.getEmail() != null ? user.getEmail() : "",
            user.getGender() != null ? user.getGender().valueOf() : "",
            user.getSecurityQuestion(),
            user.getSecurityAnswer(),
            user.getEmail() != null ? user.getEmail() : "", // avatar field
            user.getGamePlayed(),
            user.getGamePlayed(), // survival_time
            user.getGamePlayed(), // kill_count
            true, // auto_reload
            username // WHERE clause parameter
        );

        try (PreparedStatement pstmt = DataBaseInit.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error updating user", e);
        }

        logSQL(sql, params);
    }

    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (PreparedStatement pstmt = DataBaseInit.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error deleting user", e);
        }

        logSQL(sql, Arrays.asList(username));
    }

    public List<User> getUsers(String whereClause, List<Object> whereArgs,
                               String orderBy, int limit) {
        List<User> users = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM users");
        if (whereClause != null && !whereClause.trim().isEmpty()) {
            sqlBuilder.append(" WHERE ").append(whereClause);
        }
        if (orderBy != null && !orderBy.trim().isEmpty()) {
            sqlBuilder.append(" ORDER BY ").append(orderBy);
        }
        if (limit > 0) {
            sqlBuilder.append(" LIMIT ").append(limit);
        }

        String sql = sqlBuilder.toString();

        try (PreparedStatement pstmt = DataBaseInit.getConnection().prepareStatement(sql)) {
            if (whereArgs != null) {
                for (int i = 0; i < whereArgs.size(); i++) {
                    pstmt.setObject(i + 1, whereArgs.get(i));
                }
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
//                User user = new User(
//                    rs.getString("username"),
//                    rs.getString("password"),
//                    rs.getString("question_security_question"),
//                    rs.getString("answer_security_question"),
//                    rs.getString("avatar")
//                );
//                user.setScore(rs.getInt("score"));
//                user.setMostSurvivalTime(rs.getInt("survival_time"));
//                user.setKillNumber(rs.getInt("kill_count"));
//                user.setAutoReloadingEnable(rs.getBoolean("auto_reload"));
//                users.add(user);
            }
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error retrieving users with custom query", e);
        }

        logSQL(sql, whereArgs);
        return users;
    }
}
