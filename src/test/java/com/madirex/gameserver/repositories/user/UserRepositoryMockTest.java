package com.madirex.gameserver.repositories.user;

import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.UserRepository;
import org.junit.jupiter.api.*;
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
public class UserRepositoryMockTest {
    @MockBean
    private UserRepository userRepository;

    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    @Test
    @Order(1)
    void save() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User res = userRepository.save(user);
        assertAll(
                () -> assertEquals(user, res),
                () -> assertEquals(user.getId(), res.getId()),
                () -> assertEquals(user.getUsername(), res.getUsername()),
                () -> assertEquals(user.getEmail(), res.getEmail()),
                () -> assertEquals(user.getPassword(), res.getPassword())
        );

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }


    @Test
    @Order(2)
    void findById() {
        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        Optional<User> userOpt = userRepository.findById(user.getId());
        assumeTrue(userOpt.isPresent());
        var res = userOpt.get();
        assertAll(
                () -> assertEquals(user, res),
                () -> assertEquals(user.getId(), res.getId()),
                () -> assertEquals(user.getUsername(), res.getUsername()),
                () -> assertEquals(user.getEmail(), res.getEmail()),
                () -> assertEquals(user.getPassword(), res.getPassword())
        );

        Mockito.verify(userRepository, Mockito.times(1))
                .findById(user.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(userRepository.findAll())
                .thenReturn(List.of(user));
        List<User> res = userRepository.findAll();
        assertAll(
                () -> assertEquals(user, res.get(0)),
                () -> assertEquals(user.getId(), res.get(0).getId()),
                () -> assertEquals(user.getUsername(), res.get(0).getUsername()),
                () -> assertEquals(user.getEmail(), res.get(0).getEmail()),
                () -> assertEquals(user.getPassword(), res.get(0).getPassword())
        );

        Mockito.verify(userRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(userRepository.save(user))
                .thenReturn(user);
        var res = userRepository.save(user);
        assertAll(
                () -> assertEquals(user, res),
                () -> assertEquals(user.getId(), res.getId()),
                () -> assertEquals(user.getUsername(), res.getUsername()),
                () -> assertEquals(user.getEmail(), res.getEmail()),
                () -> assertEquals(user.getPassword(), res.getPassword())
        );

        Mockito.verify(userRepository, Mockito.times(1))
                .save(user);
    }

    @Test
    @Order(5)
    void delete() {
        Mockito.doNothing().when(userRepository).delete(user);
        userRepository.delete(user);
        Mockito.verify(userRepository, Mockito.times(1))
                .delete(user);
    }
}