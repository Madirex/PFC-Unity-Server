package com.madirex.gameserver.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shop")
@ToString
public class Shop {
    private String id;

    @Column(unique = true)
    private String shopName;

    private List<Item> items;

    public Shop(String shopName) {
        this.id = UUID.randomUUID().toString();
        this.shopName = shopName;
        this.items = new ArrayList<>();
    }

    @Id
    @NotBlank(message = "El id no puede estar vacío")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(unique = true)
    @NotBlank(message = "El nombre de la tienda no puede estar vacío")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shop", cascade = CascadeType.REFRESH)
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}