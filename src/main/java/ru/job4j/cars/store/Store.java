package ru.job4j.cars.store;

import ru.job4j.cars.model.Item;
import ru.job4j.cars.model.User;

import java.util.List;

public interface Store {
    List<Item> getLastDayItems();
    List<Item> getAllItems();
    List<Item> getItemsWithPhoto();
    List<Item> getItemsOfMark(String mark);
    void addItem(Item item, User user);
    void updateItem(Item item);
    Item findItemById(int id);
    User findUserByEmail(String email);
    void createUser(User user);
    void deleteItem(int itemId, int userId);
}