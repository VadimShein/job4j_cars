package ru.job4j.cars.servlet;

import ru.job4j.cars.model.User;
import ru.job4j.cars.store.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegServlet  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User findUser = UserStore.instOf().findUserByEmail(email);
        if (findUser == null) {
            HttpSession sc = req.getSession();
            User user = new User(name, email, password);
            sc.setAttribute("user", user);
            UserStore.instOf().createUser(user);
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            req.setAttribute("error", "email уже был использован");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}