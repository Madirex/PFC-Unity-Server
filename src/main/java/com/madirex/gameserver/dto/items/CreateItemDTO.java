package com.madirex.gameserver.dto.items;

import com.madirex.gameserver.model.ItemType;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemDTO {
    private User user;
    private Shop shop;

    @NotNull(message = "El nombre no puede ser nulo")
    private String name;

    @NotNull(message = "El precio no puede ser nulo")
    private int price;

    @NotNull(message = "El tipo de item no puede ser nulo")
    private ItemType itemType;

    @NotNull(message = "La cantidad de power (amountPower) no puede ser nulo")
    private double amountPower;
}