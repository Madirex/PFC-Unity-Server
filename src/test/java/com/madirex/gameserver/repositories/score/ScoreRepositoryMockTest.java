package com.madirex.gameserver.repositories.score;

import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ScoreRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScoreRepositoryMockTest {
    @MockBean
    private ScoreRepository scoreRepository;

    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    private final Score score = Score.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .user(user)
            .level(3)
            .date(LocalDateTime.now())
            .amount(23)
            .build();

    @Test
    @Order(1)
    void save() {
        Mockito.when(scoreRepository.save(score)).thenReturn(score);
        Score res = scoreRepository.save(score);
        assertAll(
                () -> assertEquals(score, res),
                () -> assertEquals(score.getId(), res.getId()),
                () -> assertEquals(score.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(score.getDate(), res.getDate()),
                () -> assertEquals(score.getAmount(), res.getAmount())
        );

        Mockito.verify(scoreRepository, Mockito.times(1)).save(score);
    }


    @Test
    @Order(2)
    void findById() {
        Mockito.when(scoreRepository.findById(score.getId()))
                .thenReturn(Optional.of(score));

        Optional<Score> scoreOpt = scoreRepository.findById(score.getId());
        assumeTrue(scoreOpt.isPresent());
        var res = scoreOpt.get();
        assertAll(
                () -> assertEquals(score, res),
                () -> assertEquals(score.getId(), res.getId()),
                () -> assertEquals(score.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(score.getDate(), res.getDate()),
                () -> assertEquals(score.getAmount(), res.getAmount())
        );

        Mockito.verify(scoreRepository, Mockito.times(1))
                .findById(score.getId());
    }

    @Test
    @Order(3)
    void findAll() {
        Mockito.when(scoreRepository.findAll())
                .thenReturn(List.of(score));
        List<Score> res = scoreRepository.findAll();
        assertAll(
                () -> assertEquals(List.of(score), res),
                () -> assertEquals(score.getId(), res.get(0).getId()),
                () -> assertEquals(score.getUser().getUsername(), res.get(0).getUser().getUsername()),
                () -> assertEquals(score.getDate(), res.get(0).getDate()),
                () -> assertEquals(score.getAmount(), res.get(0).getAmount())
        );

        Mockito.verify(scoreRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    @Order(4)
    void update() {
        Mockito.when(scoreRepository.save(score))
                .thenReturn(score);
        var res = scoreRepository.save(score);
        assertAll(
                () -> assertEquals(score, res),
                () -> assertEquals(score.getId(), res.getId()),
                () -> assertEquals(score.getId(), res.getId()),
                () -> assertEquals(score.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(score.getDate(), res.getDate()),
                () -> assertEquals(score.getAmount(), res.getAmount())
        );

        Mockito.verify(scoreRepository, Mockito.times(1))
                .save(score);
    }

    @Test
    @Order(5)
    void delete() {
        Mockito.doNothing().when(scoreRepository).delete(score);
        scoreRepository.delete(score);
        Mockito.verify(scoreRepository, Mockito.times(1))
                .delete(score);
    }
}