package com.madirex.gameserver.controllers.item;

import com.madirex.gameserver.controllers.ItemController;
import com.madirex.gameserver.dto.items.CreateItemDTO;
import com.madirex.gameserver.dto.items.ItemDTO;
import com.madirex.gameserver.dto.items.UpdateItemDTO;
import com.madirex.gameserver.dto.shop.ShopDTO;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ItemMapper;
import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.ItemType;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ItemRepository;
import com.madirex.gameserver.services.items.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

//TODO: Agregar el resto de tests en los controladores y que tengan 100% cobertura de código
//TODO: Pasar a Kotlin
//TODO: ✏️ Documentación: documentar todo lo que he usado: Git (sistema control versiones), GitHub, Gitkraken, GitFlow, Spring Boot, Spring Security, Hibernate, JUnit, mockito, etc...

@SpringBootTest
//TODO: TEST NO FUNCIONA: Update, findById, save
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
            .itemType(ItemType.WEAPON)
            .build();

    @BeforeEach
    void setUp() {
        itemRepository.save(item1);
    }

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
                .itemType(ItemType.WEAPON)
                .build();

        Mockito.when(itemRepository.save(item1))
                .thenReturn(item1);

//        Mockito.when(itemMapper.fromDTO(itemDTO1))
//                .thenReturn(item1);

//        Mockito.when(itemMapper.toDTO(item))
//                .thenReturn(itemDTO1);

        var response = itemController.save(createItemDTO);
        var res = response.getBody();
        assert res != null;
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getItemType(), itemDTO1.getItemType()),
                () -> assertEquals(res.getPrice(), itemDTO1.getPrice()),
                () -> assertEquals(res.getAmountPower(), itemDTO1.getAmountPower())
        );

        Mockito.verify(itemRepository, Mockito.times(1))
                .save(item1);
//        Mockito.verify(itemMapper, Mockito.times(1))
//                .fromDTO(createItemDTO);
//        Mockito.verify(itemMapper, Mockito.times(1))
//                .toDTO(item);
    }

    @Test
    void update() {
        CreateItemDTO createItemDTO = new CreateItemDTO();
        createItemDTO.setItemType(itemDTO1.getItemType());
        createItemDTO.setShop(shopDTO.getId());
        createItemDTO.setAmountPower(itemDTO1.getAmountPower());
        createItemDTO.setName(itemDTO1.getName());
        createItemDTO.setPrice(itemDTO1.getPrice());

        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        assertEquals(ResponseEntity.ok(createItemDTO), itemController.save(createItemDTO));

        UpdateItemDTO updateItemDTO = new UpdateItemDTO();
        updateItemDTO.setShop(itemDTO1.getName());
        updateItemDTO.setItemType(itemDTO1.getItemType());
        updateItemDTO.setPrice(itemDTO1.getPrice());
        updateItemDTO.setAmountPower(itemDTO1.getAmountPower());

        Mockito.when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        assertEquals(ResponseEntity.ok(itemDTO1), itemController.update(itemDTO1.getId(), updateItemDTO));

        Mockito.verify(itemMapper, Mockito.times(1)).toDTO(any(Item.class));
        Mockito.verify(itemRepository, Mockito.times(1)).findById(item1.getUser().getId());
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