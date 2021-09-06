package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean isAuthor;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Item> items = new ArrayList<>();

    public User() {
    }

    public User(String name, boolean isAuthor) {
        this.name = name;
        this.isAuthor = isAuthor;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && isAuthor == user.isAuthor
                && name.equals(user.name)
                && Objects.equals(items, user.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isAuthor, items);
    }
}
