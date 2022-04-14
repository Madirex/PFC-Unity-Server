package com.madirex.gameserver.mapper;

import com.madirex.gameserver.dto.shop.ShopDTO;
import com.madirex.gameserver.model.Shop;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShopMapper {
    private final ModelMapper modelMapper;

    public ShopDTO toDTO(Shop shop) {
        return modelMapper.map(shop, ShopDTO.class);
    }

    public List<ShopDTO> toDTO(List<Shop> shops) {
        return shops.stream().map(this::toDTO).collect(Collectors.toList());
    }
}