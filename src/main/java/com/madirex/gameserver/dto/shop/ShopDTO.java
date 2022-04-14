package com.madirex.gameserver.dto.shop;

import com.madirex.gameserver.dto.items.ItemDTO;
import com.madirex.gameserver.model.Item;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {
    private String id;

    @NotBlank(message = "El nombre de la tienda no puede estar vacío")
    private String shopName;

    @NotNull(message = "Los items no pueden ser nulos")
    private List<ItemDTO> items;
}
