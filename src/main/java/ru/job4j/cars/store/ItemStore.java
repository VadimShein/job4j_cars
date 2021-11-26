package ru.job4j.cars.store;

import ru.job4j.cars.model.Item;
import java.time.LocalDate;
import java.util.List;

public class ItemStore {

    private static final class Lazy {
        private static final ItemStore INST = new ItemStore();
    }

    public static ItemStore instOf() {
        return Lazy.INST;
    }

    public List<Item> getLastDayItems(boolean withPhoto) {
        LocalDate now = LocalDate.now();
        String condition = "";
        if (withPhoto) {
            condition = " and it.photo != null";
        }
        String finalCondition = condition;
        return Store.instOf().tx(
                session -> session.createQuery("select it from Item it where it.created = :now" + finalCondition, Item.class)
                        .setParameter("now", now).getResultList());
    }

    public List<Item> getAllItems(boolean withPhoto) {
        String condition = "";
        if (withPhoto) {
            condition = " where it.photo != null";
        }
        String finalCondition = condition;
        return Store.instOf().tx(
                session -> session.createQuery("from Item it" + finalCondition, Item.class).getResultList());
    }

    public Item getItemById(int id) {
        return Store.instOf().tx(
                session -> session.createQuery("from Item it join fetch it.user where it.id = :itId", Item.class)
                        .setParameter("itId", id).uniqueResult());
    }

    public void saveOrUpdateItem(Item item) {
        Store.instOf().tx(session -> {
            session.saveOrUpdate(item);
            return item;
        });
    }

    public void deleteItem(int itemId) {
        Store.instOf().tx(session -> {
            Item item = session.get(Item.class, itemId);
            session.delete(item);
            return item;
        });
    }
}
