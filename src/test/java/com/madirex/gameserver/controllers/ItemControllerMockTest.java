package com.madirex.gameserver.controllers;

import com.madirex.gameserver.dto.items.CreateItemDTO;
import com.madirex.gameserver.dto.items.ItemDTO;
import com.madirex.gameserver.dto.items.UpdateItemDTO;
import com.madirex.gameserver.dto.shop.ShopDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ItemMapper;
import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.ItemType;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ItemRepository;
import com.madirex.gameserver.services.items.ItemService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemControllerMockTest {

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemMapper itemMapper;

    @MockBean
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    @Autowired
    public ItemControllerMockTest(ItemService itemService, ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
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
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .shop(shop)
            .user(user)
            .name("item1")
            .price(100)
            .amountPower(10.0)
            .build();

    Item itemCreate = Item.builder()
            .id("ec272c62-9d31-42op-c239-0242ac120009")
            .shop(shop)
            .user(user)
            .name("item1")
            .price(100)
            .amountPower(10.0)
            .build();

    ItemDTO itemDTO1 = ItemDTO.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .name("item1")
            .price(100)
            .amountPower(10.0)
            .itemType(ItemType.WEAPON)
            .build();

    @BeforeEach
    void setUp() {
        itemRepository.save(item1);
    }

    @Test
    @Order(1)
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
    @Order(2)
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
    @Order(3)
    void findById() {
        Mockito.when(itemService.findItemById(item1.getId())).thenReturn(Optional.of(item1));
        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        Mockito.when(itemService.findItemById("notanID")).thenReturn(Optional.empty());

        assertThrows(GeneralNotFoundException.class, () -> {
            itemController.findById("notanID");
        });
    }

    @Test
    void buyItem() {
        Mockito.when(itemService.findItemById(item1.getId())).thenReturn(Optional.of(item1));
        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        Mockito.when(itemService.findItemById("notanID")).thenReturn(Optional.empty());

    }

    @Test
    @Order(4)
    void save() {
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemType(itemDTO1.getItemType());
        createItemDTO.setShop(shopDTO.getId());
        createItemDTO.setAmountPower(itemDTO1.getAmountPower());
        createItemDTO.setName(itemDTO1.getName());
        createItemDTO.setPrice(itemDTO1.getPrice());

        Item item = Item.builder()
                .itemType(itemDTO1.getItemType())
                .amountPower(itemDTO1.getAmountPower())
                .name(itemDTO1.getName())
                .price(itemDTO1.getPrice())
                .build();

        Mockito.when(itemRepository.save(item))
                .thenReturn(item);

        Mockito.when(itemMapper.toDTO(item))
                .thenReturn(itemDTO1);

        var response = itemController.save(createItemDTO);
        assert response != null;

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value())
        );

    }

    @Test
    @Order(5)
    void update() {
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemType(itemDTO1.getItemType());
        createItemDTO.setShop(shopDTO.getId());
        createItemDTO.setAmountPower(itemDTO1.getAmountPower());
        createItemDTO.setName(itemDTO1.getName());
        createItemDTO.setPrice(itemDTO1.getPrice());

        UpdateItemDTO updated = new UpdateItemDTO();

        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        assertEquals(200, itemController.save(createItemDTO).getStatusCodeValue());

        assertThrows(GeneralNotFoundException.class, () -> {
            itemController.update(item1.getId(),updated);
        });

        UpdateItemDTO updateItemDTO = new UpdateItemDTO();
        updateItemDTO.setShop(itemDTO1.getName());
        updateItemDTO.setItemType(itemDTO1.getItemType());
        updateItemDTO.setPrice(itemDTO1.getPrice());
        updateItemDTO.setAmountPower(itemDTO1.getAmountPower());

        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
    }

    @Test
    @Order(6)
    void delete() {
        Mockito.when(itemService.deleteItem(any(Item.class))).thenReturn(item1);
        Mockito.when(itemService.findItemById(item1.getId())).thenReturn(Optional.of(item1));
        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);

        assertEquals(itemController.delete(item1.getId()), ResponseEntity.ok(itemDTO1));

        assertThrows(GeneralNotFoundException.class, () -> {
            itemController.delete("dfsfsdfsdsdgsdg dfgdf");
        });

        Mockito.verify(itemService, Mockito.times(1)).findItemById(item1.getId());
        Mockito.verify(itemService, Mockito.times(1)).deleteItem(any(Item.class));
        Mockito.verify(itemMapper, Mockito.times(1)).toDTO(any(Item.class));
    }
}