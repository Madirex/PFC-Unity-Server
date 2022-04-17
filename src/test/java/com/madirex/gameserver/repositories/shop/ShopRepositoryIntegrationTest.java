package com.madirex.gameserver.repositories.shop;

import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ShopRepository;
import com.madirex.gameserver.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ShopRepositoryIntegrationTest {
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
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @Order(1)
    void save() {
        Shop res = shopRepository.save(shop);

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(shop.getShopName(), res.getShopName())
        );
    }

    @Test
    @Order(2)
    void getAllShop() {
        shopRepository.save(shop);
        assertEquals(1, shopRepository.findAll().size());
    }

    @Test
    @Order(3)
    void getShopById() {
        Shop log = shopRepository.save(shop);
        Optional<Shop> shopOpt = shopRepository.findById(log.getId());
        assumeTrue(shopOpt.isPresent());
        Shop res = shopOpt.get();

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(shop.getShopName(), res.getShopName())
        );
    }

    @Test
    @Order(4)
    void updateShop() {
        Shop logi = shopRepository.save(shop);
        Optional<Shop> shopOpt = shopRepository.findById(logi.getId());
        assumeTrue(shopOpt.isPresent());
        logi = shopOpt.get();
        Shop res = shopRepository.save(logi);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(shop.getShopName(), res.getShopName())
        );
    }

    @Test
    @Order(5)
    public void deleteShop() {
        Shop res = shopRepository.save(shop);
        Optional<Shop> shopOpt = shopRepository.findById(res.getId());
        assumeTrue(shopOpt.isPresent());
        res = shopOpt.get();
        shopRepository.delete(res);
        assertNull(shopRepository.findById(res.getId()).orElse(null));

    }
}