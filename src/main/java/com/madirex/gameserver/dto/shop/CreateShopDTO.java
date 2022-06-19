package com.madirex.gameserver.dto.shop;

import com.madirex.gameserver.model.Item;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShopDTO {
    @NotBlank(message = "El nombre de la tienda no puede estar vacío")
    private String shopName;
}
