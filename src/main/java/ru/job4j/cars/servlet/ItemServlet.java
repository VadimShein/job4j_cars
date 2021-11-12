package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Item;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

public class ItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        User user = (User) req.getSession().getAttribute("user");

        if ("deletePhoto".equals(req.getParameter("action"))) {
            int itemId = Integer.parseInt(req.getParameter("itemId"));
            if (PsqlStore.instOf().findItemById(itemId).getPhoto() != null) {
                Files.delete(Paths.get("c:\\images\\" + itemId + ".JPG"));
            }
                Item item = PsqlStore.instOf().findItemById(itemId);
                item.setPhoto(null);
                PsqlStore.instOf().addItem(item, user);
                req.getRequestDispatcher("edit.jsp").forward(req, resp);
        } else if ("close".equals(req.getParameter("action"))) {
            Item item = PsqlStore.instOf().findItemById(Integer.parseInt(req.getParameter("itemId")));
            item.setActive(Boolean.parseBoolean(req.getParameter("isActive")));
            PsqlStore.instOf().addItem(item, user);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            LocalDate date;
            if (req.getParameter("created") == null) {
                date = LocalDate.now();
            } else {
                date = LocalDate.parse(req.getParameter("created"));
            }
            Item item = new Item(
                    req.getParameter("mark"),
                    req.getParameter("model"),
                    req.getParameter("bodyType"),
                    req.getParameter("description"),
                    date,
                    user.getName()
            );
            if (req.getParameter("itemId") != null) {
                item.setId(Integer.parseInt(req.getParameter("itemId")));
                item.setActive(Boolean.parseBoolean(req.getParameter("active")));
            }
            item.setPhoto(req.getParameter("photo"));
            PsqlStore.instOf().addItem(item, user);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
