package com.madirex.gameserver.repositories.item;

import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ItemRepository;
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
import java.time.Instant;
import java.util.Date;

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
public class ItemRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;
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

    private final Item item = Item.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .user(user)
            .amountPower(20.0)
            .price(23)
            .build();


    @Test
    @Order(1)
    void save() {
        Item saved = itemRepository.save(item);
        assertAll(
                () -> assertEquals(item.getUser().getUsername(), saved.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), saved.getAmountPower()),
                () -> assertEquals(item.getPrice(), saved.getPrice())
        );
    }

    @Test
    @Order(2)
    void getAllTest() {
        entityManager.persist(item);
        entityManager.flush();

        assertTrue(itemRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getByIdTest() {
        entityManager.persist(item);
        entityManager.flush();

        Item found = itemRepository.findById(item.getId()).get();
        assertAll(
                () -> assertEquals(item.getUser().getUsername(), found.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), found.getAmountPower()),
                () -> assertEquals(item.getPrice(), found.getPrice())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(item);
        entityManager.flush();

        Item found = itemRepository.findById(item.getId()).get();
        Item updated = itemRepository.save(found);
        assertAll(
                () -> assertEquals(item.getUser().getUsername(), updated.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), updated.getAmountPower()),
                () -> assertEquals(item.getPrice(), updated.getPrice())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(item);
        entityManager.flush();
        Item res = itemRepository.findById(item.getId()).get();
        itemRepository.delete(item);
        res = itemRepository.findById(item.getId()).orElse(null);
        assertNull(res);
    }
}