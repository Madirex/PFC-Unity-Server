package com.madirex.gameserver.dto.items;

import com.madirex.gameserver.model.ItemType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemDTO {
    private String user;

    private String shop;

    @NotNull(message = "El nombre no puede ser nulo")
    private String name;

    private int price;

    @NotNull(message = "El tipo de ítem no puede ser nulo")
    private ItemType itemType;

    private double amountPower;

}