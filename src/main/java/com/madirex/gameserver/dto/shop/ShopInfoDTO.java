package com.madirex.gameserver.dto.shop;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoDTO {
    private String id;

    @NotBlank(message = "El nombre de la tienda no puede estar vacío")
    private String shopName;
}
