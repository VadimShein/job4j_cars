package ru.job4j.cars.servlet;

import org.json.JSONObject;
import ru.job4j.cars.model.Item;
import ru.job4j.cars.store.PsqlStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IndexServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        JSONObject jsonObj = new JSONObject();
        List<Item> items = new ArrayList<>();

        if (req.getParameter("allItems") != null) {
            if ("true".equals(req.getParameter("allItems"))) {
                items = PsqlStore.instOf().getAllItems();
            } else {
                items = PsqlStore.instOf().getLastDayItems();
            }
        } else if (req.getParameter("itemId") != null) {
            Item item = PsqlStore.instOf().findItemById(Integer.parseInt(req.getParameter("itemId")));
            items.add(item);
        }
        jsonObj.put("items", items);
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        writer.println(jsonObj.toString());
    }
}
