package com.madirex.gameserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
@ToString
public class Item {
    @Column(unique = true)
    private String id;

    private User user;

    private Shop shop;

    private String name;

    private int price;

    private ItemType itemType;

    private double amountPower;

    public Item(User user, Shop shop, String name, int price, ItemType itemType, double amountPower) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.shop = shop;
        this.name = name;
        this.price = price;
        this.itemType = itemType;
        this.amountPower = amountPower;
    }

    @Id
    @NotBlank(message = "El id no puede estar vacío")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = true)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "shop", referencedColumnName = "id", nullable = true)
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @NotBlank(message = "El nombre del ítem no puede estar vacío")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank(message = "El precio del ítem no puede estar vacío")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    @Enumerated(EnumType.STRING)
    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    @NotBlank(message = "La cantidad de poder a aplicar del ítem no puede estar vacío (amountPower)")
    public double getAmountPower() {
        return amountPower;
    }

    public void setAmountPower(double amountPower) {
        this.amountPower = amountPower;
    }
}