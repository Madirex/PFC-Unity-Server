package com.madirex.gameserver.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
@ToString
public class Item {
    private String id;

    @Column(nullable = true)
    private User user;

    @Column(nullable = true)
    private String shopId;

    private String name;

    private int price;

    private ItemType itemType;

    private double amountPower;

    public Item(User user, String shopId, String name, int price, ItemType itemType, double amountPower) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.shopId = shopId;
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
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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