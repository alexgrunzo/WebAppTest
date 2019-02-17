package ro.sci.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ro.sci.db.*;
import ro.sci.model.*;

import java.sql.*;

import ro.sci.servlet.UsersServlet;

import javax.swing.plaf.nimbus.State;

public class SQLUsersDAO {

    private UsersDb db;

    public SQLUsersDAO(UsersDb db) {
        this.db = db;
    }

    public void add(User user) throws UsersDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO users(username, password) values('" + user.getUsername() + "', '" + user.getPassword() + "');");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CURRVAL('users_id_seq')");
            resultSet.next();
            user.setId(resultSet.getInt(1));
        }
    }

    public List<User> getUsers() throws UsersDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * from users;");
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = mapResultSetToNewUser(resultSet);
                users.add(user);
            }
            return users;
        }
    }

    public String getUsername(int userId) throws UsersDbException, SQLException {
        String username;
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT username FROM users WHERE users.id = '" + userId + "';");
            username = null;
            while (resultSet.next()) {
                username = resultSet.getString("username");
            }

        }
        return username;
    }




    private User mapResultSetToNewUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }

}


