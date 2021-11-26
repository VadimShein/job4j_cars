package ru.job4j.cars.servlet;

import org.json.JSONObject;
import ru.job4j.cars.model.Item;
import ru.job4j.cars.store.ItemStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class IndexServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        JSONObject jsonObj = new JSONObject();
        List<Item> items = new ArrayList<>();
        Comparator<Item> comp = null;

        if (req.getParameter("allItems") != null) {
            if ("true".equals(req.getParameter("allItems"))) {
                items = ItemStore.instOf().getAllItems(Boolean.parseBoolean(req.getParameter("withPhoto")));
            } else {
                items = ItemStore.instOf().getLastDayItems(Boolean.parseBoolean(req.getParameter("withPhoto")));
            }
        } else if (req.getParameter("itemId") != null) {
            Item item = ItemStore.instOf().getItemById(Integer.parseInt(req.getParameter("itemId")));
            items.add(item);
        }
        if ("mark".equals(req.getParameter("sort"))) {
            comp = Comparator.comparing(Item::getMark);
        } else if ("date".equals(req.getParameter("sort"))) {
            comp = Comparator.comparing(Item::getCreated);
        } else if ("bodyType".equals(req.getParameter("sort"))) {
            comp = Comparator.comparing(Item::getBodyType);
        } else if ("price".equals(req.getParameter("sort"))) {
            comp = Comparator.comparing(Item::getPrice);
        }
        items.sort(comp);
        jsonObj.put("items", items);
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        writer.println(jsonObj.toString());
    }
}
