package ro.sci.service;

import ro.sci.db.UsersDb;
import ro.sci.db.UsersDbException;
import ro.sci.model.User;
import ro.sci.model.UserRole;
import ro.sci.sql.SQLRoleDAO;
import ro.sci.sql.SQLUsersDAO;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private SQLUsersDAO usersDAO;
    private SQLRoleDAO roleDAO;

    public UserService(UsersDb usersDb) {
        this.usersDAO = new SQLUsersDAO(usersDb);
        this.roleDAO = new SQLRoleDAO(usersDb);
    }

    public void addUser(User user) throws SQLException, UsersDbException {
        usersDAO.add(user);

    }

    public List<User> getUsers() throws SQLException, UsersDbException {
        List<User> users = null;
        users = usersDAO.getUsers();
        return users;
    }

    public void assignRole(int roleId, int userId) {

        try {
            roleDAO.assignRole(roleId, userId);
        } catch (UsersDbException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserRole getUserRoleForConfirmationMsg(int roleId, int userId) throws SQLException, UsersDbException {
        UserRole userRoleForConfirmationMsg = new UserRole();
        userRoleForConfirmationMsg.setUserName(usersDAO.getUsername(userId));
        userRoleForConfirmationMsg.setRoleName(roleDAO.getRolename(roleId));
        return userRoleForConfirmationMsg;
    }

    public List<UserRole> getRoles() {
        List<UserRole> userRoles = null;
        try {
            userRoles = roleDAO.getRoles();

        } catch (UsersDbException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRoles;
    }
}
