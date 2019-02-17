package ro.sci.sql;

import ro.sci.db.UsersDb;
import ro.sci.db.UsersDbException;
import ro.sci.model.UserRole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLRoleDAO {

    private UsersDb db;

    public SQLRoleDAO(UsersDb db) {
        this.db = db;
    }

    public void assignRole(int roleId, int userId) throws UsersDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO user_roles(user_id, role_id) values ('" + userId + "' , '" + roleId + "');");
        }
    }

    public List<UserRole> getRoles() throws UsersDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT user_roles.id, roles.name, users.username FROM user_roles INNER JOIN roles ON roles.id=user_roles.role_id INNER JOIN users ON users.id=user_roles.user_id;");
            List<UserRole> userRoles = new ArrayList<>();
            while (resultSet.next()) {
                UserRole userRole = mapResultSetToNewUserRole(resultSet);
                userRoles.add(userRole);
            }
            return userRoles;
        }

    }

    public String getRolename(int roleId) throws UsersDbException, SQLException {
        String rolename = null;
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT name FROM roles WHERE roles.id = '" + roleId + "';");

            while (resultSet.next()) {
                rolename = resultSet.getString("name");
            }
        }
        return rolename;
    }

    private UserRole mapResultSetToNewUserRole(ResultSet resultSet) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setId(resultSet.getInt("id"));
        userRole.setRoleName(resultSet.getString("name"));
        userRole.setUserName(resultSet.getString("username"));
        return userRole;
    }

}
