package ru.job4j.cars.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.Item;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

public class AdRepository {
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
            session.getTransaction().rollback();
            throw e;
        }
    }

    public List<Item> getLastItems() {
        LocalDate fromDate = LocalDate.now().minusDays(1);
        return this.tx(
                session -> session.createQuery("select it from Item it "
                        + "where it.created > :fromDate", Item.class)
                        .setParameter("fromDate", fromDate) .getResultList());
    }

    public List<Item> getItemsWithPhoto() {
        return this.tx(
                session -> session.createQuery("select it from Item it where it.photo != null", Item.class).getResultList());
    }

    public List<Item> getItemsOfMark(String mark) {
        return this.tx(
                session -> session.createQuery("select it from Item it where it.mark = :itMark", Item.class)
                        .setParameter("itMark", mark) .getResultList());
    }
}
