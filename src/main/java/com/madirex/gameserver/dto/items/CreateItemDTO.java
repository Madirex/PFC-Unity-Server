package com.madirex.gameserver.dto.items;

import com.madirex.gameserver.model.ItemType;
import com.madirex.gameserver.model.Shop;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemDTO {

    @NotNull(message = "La tienda no puede ser nulo")
    private String shop;

    @NotNull(message = "El nombre no puede ser nulo")
    private String name;

    private int price;

    @NotNull(message = "El tipo de ítem no puede ser nulo")
    private ItemType itemType;

    private double amountPower;

}