package controllers;

import domains.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet("/registration")
public class Registration extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("user_name");
        String email = request.getParameter("user_email");
        String password = request.getParameter("user_password");
        String type = request.getParameter("type");
        String job = request.getParameter("user_job");
        User user = new User();
        user.setU_name(name);
        user.setU_email(email);
        user.setU_password(password);
        if (type.equals("developer")) {
            user.setU_is_admin(false);
            user.setU_about(job);
        } else {
            user.setU_is_admin(true);
            user.setU_about("Admin");
        }
        try {
            User tempUser = Factory.getInstance().getUserDao().loginUser(user.getU_email(), user.getU_password());
            if (tempUser != null) {
                request.getRequestDispatcher("signup.jsp").forward(request, response);
            } else {
                Factory.getInstance().getUserDao().addUser(user);
                request.getRequestDispatcher("home").forward(request, response);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
