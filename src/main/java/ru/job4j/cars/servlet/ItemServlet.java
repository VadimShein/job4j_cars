package ru.job4j.cars.servlet;

import ru.job4j.cars.model.Item;
import ru.job4j.cars.model.User;
import ru.job4j.cars.store.ItemStore;

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
            Item item = ItemStore.instOf().getItemById(itemId);
            if (item.getPhoto() != null) {
                Files.delete(Paths.get("c:\\images\\" + itemId + ".JPG"));
            }
            item.setPhoto(null);
            ItemStore.instOf().saveOrUpdateItem(item);
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        } else if ("close".equals(req.getParameter("action"))) {
            Item item = ItemStore.instOf().getItemById(Integer.parseInt(req.getParameter("itemId")));
            item.setActive(Boolean.parseBoolean(req.getParameter("isActive")));
            ItemStore.instOf().saveOrUpdateItem(item);
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
                    req.getParameter("description").trim(),
                    Integer.parseInt(req.getParameter("price")),
                    date,
                    user.getName()
            );
            if (req.getParameter("itemId") != null) {
                item.setId(Integer.parseInt(req.getParameter("itemId")));
                item.setActive(Boolean.parseBoolean(req.getParameter("active")));
            }
            item.setPhoto(req.getParameter("photo"));
            item.setUser(user);
            ItemStore.instOf().saveOrUpdateItem(item);
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}
