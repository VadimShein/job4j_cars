package ru.job4j.cars.store;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Item;
import ru.job4j.cars.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class PsqlStore implements Store {
    private static final Logger LOG = LogManager.getLogger(PsqlStore.class.getName());

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        try (session) {
            final Transaction tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            session.getTransaction().rollback();
            throw e;
        }
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public List<Item> getLastDayItems() {
        LocalDate now = LocalDate.now();
        return this.tx(
                session -> session.createQuery("select it from Item it where it.created = :now", Item.class)
                        .setParameter("now", now) .getResultList());
    }

    @Override
    public List<Item> getAllItems() {
        return this.tx(
                session -> session.createQuery("from Item it", Item.class).getResultList());
    }

    @Override
    public List<Item> getItemsWithPhoto() {
        return this.tx(
                session -> session.createQuery("select it from Item it where it.photo != null", Item.class).getResultList());
    }

    @Override
    public List<Item> getItemsOfMark(String mark) {
        return this.tx(
                session -> session.createQuery("select it from Item it where it.mark = :itMark", Item.class)
                        .setParameter("itMark", mark) .getResultList());
    }

    @Override
    public void addItem(Item item, User user) {
        this.tx(session -> {
            session.saveOrUpdate(item);
            User updatedUser = session.get(User.class, user.getId());
            updatedUser.addItem(item);
            session.update(updatedUser);
            return true;
        });
    }

    @Override
    public void updateItem(Item item) {
        this.tx(session -> {
            session.update(item);
            return item;
        });
    }

    @Override
    public Item findItemById(int id) {
        return this.tx(
                session -> session.createQuery("from Item it where it.id = :itId", Item.class)
                        .setParameter("itId", id).uniqueResult());
    }

    @Override
    public void createUser(User user) {
        this.tx(session -> session.save(user));
    }

    @Override
    public User findUserByEmail(String email) {
        return this.tx(
                session -> session.createQuery("from User u join fetch u.items where u.email = :uEmail", User.class)
                        .setParameter("uEmail", email).uniqueResult());
    }

    @Override
    public void deleteItem(int itemId, int userId) {
        this.tx(session -> {
            Item updateItem = session.get(Item.class, itemId);
            User updateUser = session.get(User.class, userId);
            updateUser.deleteItem(updateItem);
            session.update(updateUser);
            session.delete(updateItem);
            return updateItem;
        });
    }
}
