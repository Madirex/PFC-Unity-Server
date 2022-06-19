package com.madirex.gameserver.controllers;

import com.madirex.gameserver.dto.score.CreateScoreDTO;
import com.madirex.gameserver.dto.score.ScoreDTO;
import com.madirex.gameserver.dto.shop.ShopDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ScoreMapper;
import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ScoreRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.madirex.gameserver.services.scores.ScoreService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ScoreControllerMockTest {

    @MockBean
    private ScoreService ScoreService;

    @MockBean
    private ScoreMapper ScoreMapper;

    @MockBean
    private ScoreRepository ScoreRepository;

    @InjectMocks
    private ScoreController ScoreController;

    @Autowired
    public ScoreControllerMockTest(ScoreService ScoreService, ScoreMapper ScoreMapper, ScoreRepository ScoreRepository) {
        this.ScoreService = ScoreService;
        this.ScoreMapper = ScoreMapper;
        this.ScoreRepository = ScoreRepository;
    }

    User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120007")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    Shop shop = Shop.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
            .shopName("nombre")
            .build();

    ShopDTO shopDTO = ShopDTO.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
            .shopName("nombre")
            .build();

    Score Score1 = Score.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .level(1)
            .amount(23)
            .user(new User())
            .build();

    Score ScoreCreate = Score.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .level(1)
            .amount(23)
            .user(user)
            .build();

    ScoreDTO ScoreDTO1 = ScoreDTO.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .level(1)
            .amount(23)
            .build();

    @BeforeEach
    void setUp() {
        ScoreRepository.save(Score1);
    }

    @Test
    @Order(1)
    void findAll() {
        Mockito.when(ScoreService.findAll()).thenReturn(List.of(Score1));

        Mockito.when(ScoreMapper.toDTO(List.of(Score1))).thenReturn(List.of(ScoreDTO1));
        assertEquals(
                ScoreController.findAll(Optional.of("1"),Optional.of(user.getId())).getStatusCode().value()
                , 200);

        assertThrows(GeneralBadRequestException.class, () -> {
            ScoreController.findAll(Optional.of("1.0"),Optional.of(user.getId()));
        });

        assertEquals(
                ScoreController.findAll(Optional.empty(),Optional.of(user.getId())).getStatusCode().value()
                , 200);

        assertEquals(
                ScoreController.findAll(Optional.of("1"),Optional.empty()).getStatusCode().value()
                , 200);

        assertEquals(
                ScoreController.findAll(Optional.empty(),Optional.empty()).getStatusCode().value()
                , 200);
    }

    @Test
    @Order(3)
    void findById() {
        Mockito.when(ScoreService.findScoreById(Score1.getId())).thenReturn(Optional.of(Score1));
        Mockito.when(ScoreMapper.toDTO(Score1)).thenReturn(ScoreDTO1);
        Mockito.when(ScoreService.findScoreById("notanID")).thenReturn(Optional.empty());

        assertThrows(GeneralNotFoundException.class, () -> {
            ScoreController.findById("notanID");
        });
    }

    @Test
    @Order(4)
    void save() {
        CreateScoreDTO createScoreDTO = new CreateScoreDTO();
        createScoreDTO.setAmount(2_332_344);
        createScoreDTO.setLevel(2);

        var response = ScoreController.buyItem(user, createScoreDTO);
        assert response != null;
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value())
        );
    }

    @Test
    @Order(5)
    void update() {
        CreateScoreDTO createScoreDTO = new CreateScoreDTO();
        createScoreDTO.setAmount(ScoreDTO1.getAmount());
        createScoreDTO.setLevel(ScoreDTO1.getLevel());

        Mockito.when(ScoreMapper.toDTO(Score1)).thenReturn(ScoreDTO1);
        assertEquals(200, ScoreController.buyItem(user, createScoreDTO).getStatusCodeValue());

        Mockito.when(ScoreMapper.toDTO(Score1)).thenReturn(ScoreDTO1);
    }

}