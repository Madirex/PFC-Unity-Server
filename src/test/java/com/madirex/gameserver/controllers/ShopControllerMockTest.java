package com.madirex.gameserver.controllers;

import com.madirex.gameserver.dto.shop.CreateShopDTO;
import com.madirex.gameserver.dto.shop.ShopDTO;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ShopMapper;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ShopRepository;
import com.madirex.gameserver.services.shops.ShopService;
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
public class ShopControllerMockTest {

    @MockBean
    private ShopService shopService;

    @MockBean
    private ShopMapper shopMapper;

    @MockBean
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopController shopController;

    @Autowired
    public ShopControllerMockTest(ShopService shopService, ShopMapper shopMapper, ShopRepository shopRepository) {
        this.shopService = shopService;
        this.shopMapper = shopMapper;
        this.shopRepository = shopRepository;
    }

    User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120007")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    Shop shop = Shop.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .shopName("tienda de prueba")
            .build();

    ShopDTO shopDTO = ShopDTO.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
            .shopName("nombre")
            .build();

    Shop shop1 = Shop.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .shopName("tienda de prueba")
            .build();

    Shop shopCreate = Shop.builder()
            .id("ec272c62-9d31-42op-c239-0242ac120009")
            .shopName("tienda de prueba")
            .build();

    ShopDTO shopDTO1 = ShopDTO.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .shopName("tienda de prueba")
            .build();

    @BeforeEach
    void setUp() {
        shopRepository.save(shop1);
    }

    @Test
    @Order(1)
    void findAll() {
        Mockito.when(shopService.findAll()).thenReturn(List.of(shop1));

        Mockito.when(shopMapper.toDTO(List.of(shop1))).thenReturn(List.of(shopDTO1));
        assertEquals(
                shopController.findAll(Optional.empty())
                , ResponseEntity.ok(List.of(shopDTO1)));

        Mockito.verify(shopService, Mockito.times(1)).findAll();
        Mockito.verify(shopMapper, Mockito.times(1)).toDTO(List.of(shop1));
    }

    @Test
    @Order(2)
    void findAllFiltered() {
        Mockito.when(shopService.findAll()).thenReturn(List.of(shop1));
        Mockito.when(shopMapper.toDTO(List.of(shop1))).thenReturn(List.of(shopDTO1));
        assertAll(
                () -> assertEquals(
                        ResponseEntity.ok(List.of(shopDTO1))
                        , shopController.findAll(Optional.empty())
                )
        );

        Mockito.verify(shopMapper, Mockito.times(1)).toDTO(List.of(shop1));
    }

    @Test
    @Order(3)
    void findById() {
        Mockito.when(shopService.findShopById(shop1.getId())).thenReturn(Optional.of(shop1));
        Mockito.when(shopMapper.toDTO(shop1)).thenReturn(shopDTO1);
        Mockito.when(shopService.findShopById("notanID")).thenReturn(Optional.empty());

        assertThrows(GeneralNotFoundException.class, () -> {
            shopController.findById("notanID");
        });
    }

    @Test
    @Order(4)
    void save() {
        CreateShopDTO createShopDTO = new CreateShopDTO();
        createShopDTO.setShopName(shopDTO1.getShopName());

        Shop shop = Shop.builder()
                .shopName(shopDTO1.getShopName())
                .build();

        Mockito.when(shopRepository.save(shop))
                .thenReturn(shop);

        Mockito.when(shopMapper.toDTO(shop))
                .thenReturn(shopDTO1);

        var response = shopController.save(createShopDTO);
        assert response != null;

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value())
        );
    }

    @Test
    @Order(5)
    void update() {
        CreateShopDTO createShopDTO = new CreateShopDTO();
        createShopDTO.setShopName(shopDTO1.getShopName());

        Mockito.when(shopMapper.toDTO(shop1)).thenReturn(shopDTO1);
        assertEquals(200, shopController.save(createShopDTO).getStatusCodeValue());

        Mockito.when(shopMapper.toDTO(shop1)).thenReturn(shopDTO1);
    }

    @Test
    @Order(6)
    void delete() {
        Mockito.when(shopService.deleteShop(any(Shop.class))).thenReturn(shop1);
        Mockito.when(shopService.findShopById(shop1.getId())).thenReturn(Optional.of(shop1));
        Mockito.when(shopMapper.toDTO(shop1)).thenReturn(shopDTO1);

        assertEquals(shopController.delete(shop1.getId()), ResponseEntity.ok(shop1));

        Mockito.verify(shopService, Mockito.times(1)).findShopById(shop1.getId());
        Mockito.verify(shopService, Mockito.times(1)).deleteShop(any(Shop.class));

        assertThrows(GeneralNotFoundException.class, () -> {
            shopController.delete("notanID");
        });
    }
}