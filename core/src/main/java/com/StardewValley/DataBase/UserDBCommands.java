package com.StardewValley.DataBase;

import com.StardewValley.model.User;
import com.badlogic.gdx.Gdx;
import com.StardewValley.model.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            "username, password, question_security_question, answer_security_question, avatar, " +
            "last_game_path, score, survival_time, kill_count, auto_reload" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List<Object> params = Arrays.asList(
            user.getUsername(),
            user.getPassword(),
            user.getSecurityQuestion(),
            user.getSecurityAnswer(),
            user.getUsername(),
            "-",
            user.getGamePlayed(),
            user.getGamePlayed(),
            user.getGamePlayed(),
            true
        );

        try (PreparedStatement pstmt = DataBaseInit.getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            pstmt.executeUpdate();
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
//                user = new User(
//                    rs.getString("username"),
//                    rs.getString("password"),
//                    rs.getString("question_security_question"),
//                    rs.getString("answer_security_question"),
//                    rs.getString("avatar")
//                );
//
//                user.setScore(rs.getInt("score"));
//                user.setMostSurvivalTime(rs.getInt("survival_time"));
//                user.setKillNumber(rs.getInt("kill_count"));
//                user.setAutoReloadingEnable(rs.getBoolean("auto_reload"));
            }

            new UserDBCommands().logSQL(sql, Arrays.asList(username));
        } catch (SQLException e) {
            Gdx.app.error("Database", "Error getting user", e);
        }

        return user;
    }

    public void updateUser(User user, String username) {
        String sql = "UPDATE users SET " +
            "username = ?," +
            "password = ?, " +
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
//            user.getQuestionSecurityQuestion(),
//            user.getAnswerSecurityQuestion(),
//            user.getAvatarPath(),
//            user.getScore(),
//            user.getMostSurvivalTime(),
//            user.getKillNumber(),
//            user.isAutoReloadingEnable(),
            App.loggedUser.getUsername()
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
