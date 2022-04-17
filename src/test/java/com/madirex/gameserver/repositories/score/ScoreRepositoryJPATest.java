package com.madirex.gameserver.repositories.score;

import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ScoreRepository;
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
public class ScoreRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScoreRepository scoreRepository;
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

    private final Score score = Score.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .user(user)
            .date(LocalDateTime.now())
            .amount(200)
            .build();


    @Test
    @Order(1)
    void save() {
        Score saved = scoreRepository.save(score);
        assertAll(
                () -> assertEquals(score.getUser().getUsername(), saved.getUser().getUsername()),
                () -> assertEquals(score.getDate(), saved.getDate()),
                () -> assertEquals(score.getAmount(), saved.getAmount())
        );
    }

    @Test
    @Order(2)
    void getAllTest() {
        entityManager.persist(score);
        entityManager.flush();

        assertTrue(scoreRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getByIdTest() {
        entityManager.persist(score);
        entityManager.flush();

        Score found = scoreRepository.findById(score.getId()).get();
        assertAll(
                () -> assertEquals(score.getUser().getUsername(), found.getUser().getUsername()),
                () -> assertEquals(score.getDate(), found.getDate()),
                () -> assertEquals(score.getAmount(), found.getAmount())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(score);
        entityManager.flush();

        Score found = scoreRepository.findById(score.getId()).get();
        Score updated = scoreRepository.save(found);
        assertAll(
                () -> assertEquals(score.getUser().getUsername(), updated.getUser().getUsername()),
                () -> assertEquals(score.getDate(), updated.getDate()),
                () -> assertEquals(score.getAmount(), updated.getAmount())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(score);
        entityManager.flush();
        Score res = scoreRepository.findById(score.getId()).get();
        scoreRepository.delete(score);
        res = scoreRepository.findById(score.getId()).orElse(null);
        assertNull(res);
    }
}