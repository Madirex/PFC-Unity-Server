package com.madirex.gameserver.controllers.item;

import com.madirex.gameserver.controllers.ItemController;
import com.madirex.gameserver.dto.items.CreateItemDTO;
import com.madirex.gameserver.dto.items.ItemDTO;
import com.madirex.gameserver.dto.items.UpdateItemDTO;
import com.madirex.gameserver.dto.shop.ShopDTO;
import com.madirex.gameserver.dto.user.UserDTO;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ItemMapper;
import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ShopRepository;
import com.madirex.gameserver.repositories.UserRepository;
import com.madirex.gameserver.services.items.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ItemControllerMockTest {

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemMapper itemMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ShopRepository shopRepository;

    @InjectMocks
    private ItemController itemController;

    @Autowired
    public ItemControllerMockTest(ItemService itemService, ItemMapper itemMapper, UserRepository userRepository, ShopRepository shopRepository) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.userRepository = userRepository;
        this.shopRepository = shopRepository;
    }

    User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120007")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    Shop shop = Shop.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
            .shopName("nombre")
            .build();

    ShopDTO shopDTO = ShopDTO.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
            .shopName("nombre")
            .build();

    Item item1 = Item.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120002")
            .shop(shop)
            .user(user)
            .name("item1")
            .price(100)
            .amountPower(10.0)
            .build();

    ItemDTO itemDTO1 = ItemDTO.builder()
            .id("0fc7d018-9d32-11ec-b909-0242ac120002")
            .name("item1")
            .price(100)
            .amountPower(10.0)
            .build();

    @Test
    void findAll() {
        Mockito.when(itemService.findAll()).thenReturn(List.of(item1));

        Mockito.when(itemMapper.toDTO(List.of(item1))).thenReturn(List.of(itemDTO1));
        assertEquals(
                itemController.findAll(Optional.empty())
                , ResponseEntity.ok(List.of(itemDTO1)));

        Mockito.verify(itemService, Mockito.times(1)).findAll();
        Mockito.verify(itemMapper, Mockito.times(1)).toDTO(List.of(item1));
    }

    @Test
    void findAllFiltered() {
        Mockito.when(itemService.findAll()).thenReturn(List.of(item1));
        Mockito.when(itemMapper.toDTO(List.of(item1))).thenReturn(List.of(itemDTO1));
        assertAll(
                () -> assertEquals(
                        ResponseEntity.ok(List.of(itemDTO1))
                        , itemController.findAll(Optional.empty())
                )
        );

        Mockito.verify(itemMapper, Mockito.times(1)).toDTO(List.of(item1));
    }

    @Test
    void findById() {
        Mockito.when(itemService.findItemById(item1.getId())).thenReturn(Optional.of(item1));
        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        Mockito.when(itemService.findItemById("notanID")).thenReturn(Optional.empty());
        Exception ex = assertThrows(GeneralNotFoundException.class, () -> {
            itemController.findById("notanID");
        });
        assertAll(
                () -> assertEquals(
                        ResponseEntity.ok(itemDTO1)
                        , itemController.findById(item1.getId())
                ),
                () -> assertTrue(ex.getMessage().contains("notanID"))
        );
        Mockito.verify(itemService, Mockito.times(1)).findItemById(item1.getId());
        Mockito.verify(itemService, Mockito.times(1)).findItemById("notanID");
        Mockito.verify(itemMapper, Mockito.times(1)).toDTO(item1);
    }

    @Test
    void save() {
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemType(itemDTO1.getItemType());
        createItemDTO.setShop(shopDTO.getId());
        createItemDTO.setAmountPower(itemDTO1.getAmountPower());
        createItemDTO.setName(itemDTO1.getName());
        createItemDTO.setPrice(itemDTO1.getPrice());

        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);

        assertEquals(ResponseEntity.ok(itemDTO1), itemController.save(createItemDTO));


        Mockito.verify(itemMapper, Mockito.times(1)).toDTO(any(Item.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(item1.getUser().getId());
    }

    @Test
    void update() {
        UpdateItemDTO updateItemDTO = new UpdateItemDTO();
        updateItemDTO.setShop(itemDTO1.getName());
        updateItemDTO.setItemType(itemDTO1.getItemType());
        updateItemDTO.setPrice(itemDTO1.getPrice());
        updateItemDTO.setAmountPower(itemDTO1.getAmountPower());

        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        assertEquals(ResponseEntity.ok(itemDTO1), itemController.update("d6eef0f1-8ce4-4d7d-8c4d-250917def843", updateItemDTO));

        Mockito.verify(itemMapper, Mockito.times(1)).toDTO(any(Item.class));
        Mockito.verify(userRepository, Mockito.times(1)).findById(item1.getUser().getId());
    }

    @Test
    void delete() {
        Mockito.when(itemService.deleteItem(any(Item.class))).thenReturn(item1);
        Mockito.when(itemService.findItemById(item1.getId())).thenReturn(Optional.of(item1));
        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);

        assertEquals(itemController.delete(item1.getId()), ResponseEntity.ok(itemDTO1));

        Mockito.verify(itemService, Mockito.times(1)).findItemById(item1.getId());
        Mockito.verify(itemService, Mockito.times(1)).deleteItem(any(Item.class));
        Mockito.verify(itemMapper, Mockito.times(1)).toDTO(any(Item.class));
    }
}