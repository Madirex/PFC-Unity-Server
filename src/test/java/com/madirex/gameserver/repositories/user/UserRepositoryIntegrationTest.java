package com.madirex.gameserver.repositories.user;

import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryIntegrationTest {
    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @Order(1)
    void save() {
        User res = userRepository.save(user);

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(user.getUsername(), res.getUsername()),
                () -> assertEquals(user.getEmail(), res.getEmail()),
                () -> assertEquals(user.getPassword(), res.getPassword())
        );
    }

    @Test
    @Order(2)
    void getAllUser() {
        userRepository.save(user);
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    @Order(3)
    void getUserById() {
        User log = userRepository.save(user);
        Optional<User> userOpt = userRepository.findById(log.getId());
        assumeTrue(userOpt.isPresent());
        User res = userOpt.get();

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(user.getUsername(), res.getUsername()),
                () -> assertEquals(user.getEmail(), res.getEmail()),
                () -> assertEquals(user.getPassword(), res.getPassword())
        );
    }

    @Test
    @Order(4)
    void updateUser() {
        User logi = userRepository.save(user);
        Optional<User> userOpt = userRepository.findById(logi.getId());
        assumeTrue(userOpt.isPresent());
        logi = userOpt.get();
        User res = userRepository.save(logi);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(user.getUsername(), res.getUsername()),
                () -> assertEquals(user.getEmail(), res.getEmail()),
                () -> assertEquals(user.getPassword(), res.getPassword())
        );
    }

    @Test
    @Order(5)
    public void deleteUser() {
        User res = userRepository.save(user);
        Optional<User> userOpt = userRepository.findById(res.getId());
        assumeTrue(userOpt.isPresent());
        res = userOpt.get();
        userRepository.delete(res);
        assertNull(userRepository.findById(res.getId()).orElse(null));

    }
}