package ro.sci.servlet;

import javafx.scene.input.DataFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.SimpleFormatter;

public class MyAgeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String date = req.getParameter("date");
        Date myAge = null;
        try {
            myAge = new SimpleDateFormat("dd-mm-yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            resp.getWriter().println(e.getMessage());
            resp.getWriter().println(date+" "+myAge);
        }
        resp.getWriter().println("Difference from " + myAge + " to current date is: "+  myAge);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String to = req.getParameter("to");
        System.out.println("to");
        resp.getWriter().println("Hello - " + to);
    }

    public long getDifference(Date myAge) {
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
        Date current = new Date();


        return  current.getTime()-myAge.getTime();
//        return daysNr;
    }

}
