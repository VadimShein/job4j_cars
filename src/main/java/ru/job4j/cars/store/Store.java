package ru.job4j.cars.store;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

public class Store {
    private static final Logger LOG = LogManager.getLogger(Store.class.getName());
    private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder().configure().build();
    private static final SessionFactory SF = new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final Store INST = new Store();
    }

    public static Store instOf() {
        return Store.Lazy.INST;
    }

    public static ItemStore getItemStore() {
        return ItemStore.instOf();
    }

    public static UserStore getUserStore() {
        return UserStore.instOf();
    }

    public <T> T tx(final Function<Session, T> command) {
        final Session session = SF.openSession();
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
}
