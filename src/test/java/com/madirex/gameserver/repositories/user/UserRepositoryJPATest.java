package com.madirex.gameserver.repositories.user;

import com.madirex.gameserver.model.User;
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
public class UserRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

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

    @Test
    @Order(1)
    void save() {
        User saved = userRepository.save(user);
        assertAll(
                () -> assertEquals(user.getUsername(), saved.getUsername()),
                () -> assertEquals(user.getEmail(), saved.getEmail()),
                () -> assertEquals(user.getPassword(), saved.getPassword())
        );
    }

    @Test
    @Order(2)
    void getAllTest() {
        entityManager.persist(user);
        entityManager.flush();

        assertTrue(userRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getByIdTest() {
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findById(user.getId()).get();
        assertAll(
                () -> assertEquals(user.getUsername(), found.getUsername()),
                () -> assertEquals(user.getEmail(), found.getEmail()),
                () -> assertEquals(user.getPassword(), found.getPassword())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findById(user.getId()).get();
        User updated = userRepository.save(found);
        assertAll(
                () -> assertEquals(user.getUsername(), updated.getUsername()),
                () -> assertEquals(user.getEmail(), updated.getEmail()),
                () -> assertEquals(user.getPassword(), updated.getPassword())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(user);
        entityManager.flush();
        User res = userRepository.findById(user.getId()).get();
        userRepository.delete(user);
        res = userRepository.findById(user.getId()).orElse(null);
        assertNull(res);
    }
}