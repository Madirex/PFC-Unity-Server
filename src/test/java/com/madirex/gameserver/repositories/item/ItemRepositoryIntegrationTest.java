package com.madirex.gameserver.repositories.item;

import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ItemRepository;
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
public class ItemRepositoryIntegrationTest {
    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    private final Item item = Item.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .user(user)
            .amountPower(23.0)
            .price(23)
            .build();
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @Order(1)
    void save() {
        Item res = itemRepository.save(item);

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(item.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), res.getAmountPower()),
                () -> assertEquals(item.getPrice(), res.getPrice())
        );
    }

    @Test
    @Order(2)
    void getAllItem() {
        itemRepository.save(item);
        assertEquals(1, itemRepository.findAll().size());
    }

    @Test
    @Order(3)
    void getItemById() {
        Item log = itemRepository.save(item);
        Optional<Item> itemOpt = itemRepository.findById(log.getId());
        assumeTrue(itemOpt.isPresent());
        Item res = itemOpt.get();

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(item.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), res.getAmountPower()),
                () -> assertEquals(item.getPrice(), res.getPrice())
        );
    }

    @Test
    @Order(4)
    void updateItem() {
        Item logi = itemRepository.save(item);
        Optional<Item> itemOpt = itemRepository.findById(logi.getId());
        assumeTrue(itemOpt.isPresent());
        logi = itemOpt.get();
        Item res = itemRepository.save(logi);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(item.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), res.getAmountPower()),
                () -> assertEquals(item.getPrice(), res.getPrice())
        );
    }

    @Test
    @Order(5)
    public void deleteItem() {
        Item res = itemRepository.save(item);
        Optional<Item> itemOpt = itemRepository.findById(res.getId());
        assumeTrue(itemOpt.isPresent());
        res = itemOpt.get();
        itemRepository.delete(res);
        assertNull(itemRepository.findById(res.getId()).orElse(null));

    }
}