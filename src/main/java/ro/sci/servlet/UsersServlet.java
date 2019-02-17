package ro.sci.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ro.sci.db.UsersDb;
import ro.sci.db.UsersDbException;
import ro.sci.model.*;
import ro.sci.service.*;



public class UsersServlet extends HttpServlet{

  private UserService userService;

public void init(ServletConfig servletConfig) throws ServletException {
    UsersDb db = new UsersDb();
    userService = new UserService(db);
}

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users= null;
        try {
            users = userService.getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UsersDbException e) {
            e.printStackTrace();
        }
        resp.getWriter().println("Your user list: ");
        for (User user: users){
            resp.getWriter().println("User id: "+user.getId()+" username: "+user.getUsername()+" password: "+user.getPassword());
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String username= req.getParameter("username");
            String password= req.getParameter("password");
            User user = new User(username,password);
        try {

            userService.addUser(user);
            resp.getWriter().println("User added! id: "+user.getId()+" username: "+user.getUsername()+" password: "+user.getPassword());

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UsersDbException e) {
            e.printStackTrace();
        }


    }

}
