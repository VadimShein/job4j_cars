package ru.job4j.cars.store;

import ru.job4j.cars.model.User;

public class UserStore {

    private static final class Lazy {
        private static final UserStore INST = new UserStore();
    }

    public static UserStore instOf() {
        return UserStore.Lazy.INST;
    }

    public void createUser(User user) {
        Store.instOf().tx(session -> session.save(user));
    }

    public User findUserByEmail(String email) {
        return Store.instOf().tx(
                session -> session.createQuery("from User u where u.email = :uEmail", User.class)
                        .setParameter("uEmail", email).uniqueResult());
    }
}
