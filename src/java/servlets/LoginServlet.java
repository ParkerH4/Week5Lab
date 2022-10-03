package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.AccountService;
import models.User;

/**
 *
 * @author user
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("logout");
        String username = (String) session.getAttribute("username");

        if (action != null) {
            session.invalidate();
            request.setAttribute("result", "You have successfully logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        if (username != null) {
            session.setAttribute("username", username);
            response.sendRedirect("home");
            
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        AccountService a = new AccountService();
        User user = null;

        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            user = a.login(username, password);
        }
        
        if (user != null) {
            session.setAttribute("username", username);
            response.sendRedirect("home");
        } else {
            request.setAttribute("result", "Incorrect username or password.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

}
