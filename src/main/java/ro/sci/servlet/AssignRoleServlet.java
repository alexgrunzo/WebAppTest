package ro.sci.servlet;

import ro.sci.db.UsersDb;
import ro.sci.db.UsersDbException;
import ro.sci.model.UserRole;
import ro.sci.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AssignRoleServlet  extends HttpServlet {


    private UserService userService;

    public void init(ServletConfig servletConfig) throws ServletException {
        UsersDb db = new UsersDb();
        userService = new UserService(db);
    }
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<UserRole> userRoleList=userService.getRoles();
            resp.getWriter().println("Your role list: \n");
            for(UserRole userRole: userRoleList){
            resp.getWriter().println("Id: " + userRole.getId()+" username: "+ userRole.getUserName()+ " role: "+userRole.getRoleName());
            }

        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            int roleId = Integer.parseInt(req.getParameter("roleId"));
            int userId = Integer.parseInt(req.getParameter("userId"));

                userService.assignRole(roleId,userId);

            UserRole roleConf = null;
            try {
                roleConf = userService.getUserRoleForConfirmationMsg(roleId,userId);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UsersDbException e) {
                e.printStackTrace();
            }
            resp.getWriter().println("Assigned role! rolename: " + roleConf.getRoleName() + " username: " + roleConf.getUserName());

        }

}

