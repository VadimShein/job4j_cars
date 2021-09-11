package ru.job4j.cars.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class PUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Item> items = new ArrayList<>();

    public PUser() {
    }

    public PUser(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PUser pUser = (PUser) o;
        return id == pUser.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
