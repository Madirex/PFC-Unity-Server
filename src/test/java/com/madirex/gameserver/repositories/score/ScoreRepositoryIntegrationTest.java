package com.madirex.gameserver.repositories.score;

import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ScoreRepository;
import com.madirex.gameserver.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ScoreRepositoryIntegrationTest {
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
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @Order(1)
    void save() {
        Score res = scoreRepository.save(score);

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(score.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(score.getDate(), res.getDate()),
                () -> assertEquals(score.getAmount(), res.getAmount())
        );
    }

    @Test
    @Order(2)
    void getAllScore() {
        scoreRepository.save(score);
        assertEquals(1, scoreRepository.findAll().size());
    }

    @Test
    @Order(3)
    void getScoreById() {
        Score log = scoreRepository.save(score);
        Optional<Score> scoreOpt = scoreRepository.findById(log.getId());
        assumeTrue(scoreOpt.isPresent());
        Score res = scoreOpt.get();

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(score.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(score.getDate().truncatedTo(ChronoUnit.MINUTES), res.getDate().truncatedTo(ChronoUnit.MINUTES)),
                () -> assertEquals(score.getAmount(), res.getAmount())
        );
    }

    @Test
    @Order(4)
    void updateScore() {
        Score logi = scoreRepository.save(score);
        Optional<Score> scoreOpt = scoreRepository.findById(logi.getId());
        assumeTrue(scoreOpt.isPresent());
        logi = scoreOpt.get();
        Score res = scoreRepository.save(logi);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(score.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(score.getDate().truncatedTo(ChronoUnit.MINUTES), res.getDate().truncatedTo(ChronoUnit.MINUTES)),
                () -> assertEquals(score.getAmount(), res.getAmount())
        );
    }

    @Test
    @Order(5)
    public void deleteScore() {
        Score res = scoreRepository.save(score);
        Optional<Score> scoreOpt = scoreRepository.findById(res.getId());
        assumeTrue(scoreOpt.isPresent());
        res = scoreOpt.get();
        scoreRepository.delete(res);
        assertNull(scoreRepository.findById(res.getId()).orElse(null));

    }
}