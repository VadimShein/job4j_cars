package ru.job4j.cars.servlet;

import ru.job4j.cars.model.User;
import ru.job4j.cars.store.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User currentUser = UserStore.instOf().findUserByEmail(email);

        if (currentUser != null && currentUser.getPassword().equals(password)) {
            HttpSession sc = req.getSession();
            User user = new User(currentUser.getId(), currentUser.getName(), currentUser.getEmail(), currentUser.getPassword());
            sc.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
