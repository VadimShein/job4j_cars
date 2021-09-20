package ru.job4j.cars.model;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String mark;
    @Column(name = "body_type", nullable = false)
    private String bodyType;
    private File photo;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(nullable = false)
    private LocalDate created;

    public Item() {
    }

    public Item(String description, String mark, String bodyType, LocalDate created) {
        this.description = description;
        this.mark = mark;
        this.bodyType = bodyType;
        this.isActive = true;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
