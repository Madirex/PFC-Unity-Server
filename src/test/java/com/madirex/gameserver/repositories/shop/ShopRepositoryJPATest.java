package com.madirex.gameserver.repositories.shop;

import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ShopRepository;
import com.madirex.gameserver.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TypeExcludeFilters(value = DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        entityManager.persist(user);
    }

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
        Shop saved = shopRepository.save(shop);
        assertAll(
                () -> assertEquals(shop.getShopName(), saved.getShopName())
        );
    }

    @Test
    @Order(2)
    void getAllTest() {
        entityManager.persist(shop);
        entityManager.flush();

        assertTrue(shopRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getByIdTest() {
        entityManager.persist(shop);
        entityManager.flush();

        Shop found = shopRepository.findById(shop.getId()).get();
        assertAll(
                () -> assertEquals(shop.getShopName(), found.getShopName())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(shop);
        entityManager.flush();

        Shop found = shopRepository.findById(shop.getId()).get();
        Shop updated = shopRepository.save(found);
        assertAll(
                () -> assertEquals(shop.getShopName(), updated.getShopName())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(shop);
        entityManager.flush();
        Shop res = shopRepository.findById(shop.getId()).get();
        shopRepository.delete(shop);
        res = shopRepository.findById(shop.getId()).orElse(null);
        assertNull(res);
    }
}