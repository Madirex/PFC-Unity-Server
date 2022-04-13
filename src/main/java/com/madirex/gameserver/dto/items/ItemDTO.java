package com.madirex.gameserver.dto.items;

import com.madirex.gameserver.model.ItemType;
import com.madirex.gameserver.model.User;

import javax.validation.constraints.NotNull;

public class ItemDTO {
    private String id;
    private User user;
    private String shopId;

    @NotNull(message = "El nombre no puede ser nulo")
    private String name;

    @NotNull(message = "El precio no puede ser nulo")
    private int price;

    @NotNull(message = "El tipo de item no puede ser nulo")
    private ItemType itemType;

    @NotNull(message = "La cantidad de power (amountPower) no puede ser nulo")
    private double amountPower;
}