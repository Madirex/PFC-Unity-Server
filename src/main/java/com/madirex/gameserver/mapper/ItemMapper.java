package com.madirex.gameserver.mapper;

import com.madirex.gameserver.dto.items.ItemDTO;
import com.madirex.gameserver.model.Item;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ModelMapper modelMapper;

    /**
     * Convertir DAO a DTO
     * @param item DAO
     * @return DTO
     */
    public ItemDTO toDTO(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    /**
     * Convertir lista DAO a lista DTO
     * @param items lista DAO
     * @return lista DTO
     */
    public List<ItemDTO> toDTO(List<Item> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }
}