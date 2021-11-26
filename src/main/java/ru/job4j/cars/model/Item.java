package ru.job4j.cars.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String mark;
    @Column(nullable = false)
    private String model;
    @Column(name = "body_type", nullable = false)
    private String bodyType;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private LocalDate created;
    private String photo;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(nullable = false)
    private String author;
    @ManyToOne
    private User user;

    public Item() {
    }

    public Item(String mark, String model, String bodyType, String description, int price, LocalDate created, String author) {
        this.mark = mark;
        this.model = model;
        this.bodyType = bodyType;
        this.description = description;
        this.price = price;
        this.created = created;
        this.author = author;
        this.isActive = true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
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

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", mark='" + mark + '\''
                + ", model='" + model + '\''
                + ", bodyType='" + bodyType + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + ", created=" + created
                + ", photo='" + photo + '\''
                + ", isActive=" + isActive
                + ", author='" + author + '\''
                + ", user=" + user + '}';
    }
}

