package com.madirex.gameserver.repositories.shop;

import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ShopRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopRepositoryMockTest {
    @MockBean
    private ShopRepository shopRepository;

    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    private final Shop shop = Shop.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .shopName("nombre tienda")
            .build();

    @Test
    @Order(1)
    void save() {
        Mockito.when(shopRepository.save(shop)).thenReturn(shop);
        Shop res = shopRepository.save(shop);
        assertAll(
                () -> assertEquals(shop, res),
                () -> assertEquals(shop.getId(), res.getId()),
                () -> assertEquals(shop.getShopName(), res.getShopName())
        );

        Mockito.verify(shopRepository, Mockito.times(1)).save(shop);
    }


    @Test
    @Order(2)
    void findById() {
        Mockito.when(shopRepository.findById(shop.getId()))
                .thenReturn(Optional.of(shop));

        Optional<Shop> shopOpt = shopRepository.findById(shop.getId());
        assumeTrue(shopOpt.isPresent());
        var res = shopOpt.get();
        assertAll(
                () -> assertEquals(shop, res),
                () -> assertEquals(shop.getId(), res.getId()),
                () -> assertEquals(shop.getShopName(), res.getShopName())
        );

        Mockito.verify(shopRepository, Mockito.times(1))
                .findById(shop.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(shopRepository.findAll())
                .thenReturn(List.of(shop));
        List<Shop> res = shopRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(shop), res),
                () -> assertEquals(shop.getId(), res.get(0).getId()),
                () -> assertEquals(shop.getShopName(), res.get(0).getShopName())
        );

        Mockito.verify(shopRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(shopRepository.save(shop))
                .thenReturn(shop);
        var res = shopRepository.save(shop);
        assertAll(
                () -> assertEquals(shop, res),
                () -> assertEquals(shop.getId(), res.getId()),
                () -> assertEquals(shop.getId(), res.getId()),
                () -> assertEquals(shop.getShopName(), res.getShopName())
        );

        Mockito.verify(shopRepository, Mockito.times(1))
                .save(shop);
    }

    @Test
    @Order(5)
    void delete() {
        Mockito.doNothing().when(shopRepository).delete(shop);
        shopRepository.delete(shop);
        Mockito.verify(shopRepository, Mockito.times(1))
                .delete(shop);
    }
}