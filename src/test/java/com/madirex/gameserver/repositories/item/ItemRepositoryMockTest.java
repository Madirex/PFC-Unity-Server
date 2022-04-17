package com.madirex.gameserver.repositories.item;

import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ItemRepository;
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
public class ItemRepositoryMockTest {
    @MockBean
    private ItemRepository itemRepository;

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
        Mockito.when(itemRepository.save(item)).thenReturn(item);
        Item res = itemRepository.save(item);
        assertAll(
                () -> assertEquals(item, res),
                () -> assertEquals(item.getId(), res.getId()),
                () -> assertEquals(item.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), res.getAmountPower()),
                () -> assertEquals(item.getPrice(), res.getPrice())
        );

        Mockito.verify(itemRepository, Mockito.times(1)).save(item);
    }


    @Test
    @Order(2)
    void findById() {
        Mockito.when(itemRepository.findById(item.getId()))
                .thenReturn(Optional.of(item));

        Optional<Item> itemOpt = itemRepository.findById(item.getId());
        assumeTrue(itemOpt.isPresent());
        var res = itemOpt.get();
        assertAll(
                () -> assertEquals(item, res),
                () -> assertEquals(item.getId(), res.getId()),
                () -> assertEquals(item.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), res.getAmountPower()),
                () -> assertEquals(item.getPrice(), res.getPrice())
        );

        Mockito.verify(itemRepository, Mockito.times(1))
                .findById(item.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(itemRepository.findAll())
                .thenReturn(List.of(item));
        List<Item> res = itemRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(item), res),
                () -> assertEquals(item.getId(), res.get(0).getId()),
                () -> assertEquals(item.getUser().getUsername(), res.get(0).getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), res.get(0).getAmountPower()),
                () -> assertEquals(item.getPrice(), res.get(0).getPrice())
        );

        Mockito.verify(itemRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(itemRepository.save(item))
                .thenReturn(item);
        var res = itemRepository.save(item);
        assertAll(
                () -> assertEquals(item, res),
                () -> assertEquals(item.getId(), res.getId()),
                () -> assertEquals(item.getId(), res.getId()),
                () -> assertEquals(item.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(item.getAmountPower(), res.getAmountPower()),
                () -> assertEquals(item.getPrice(), res.getPrice())
        );

        Mockito.verify(itemRepository, Mockito.times(1))
                .save(item);
    }

    @Test
    @Order(5)
    void delete() {
        Mockito.doNothing().when(itemRepository).delete(item);
        itemRepository.delete(item);
        Mockito.verify(itemRepository, Mockito.times(1))
                .delete(item);
    }
}