package com.madirex.gameserver.controllers.login;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.controllers.LoginController;
import com.madirex.gameserver.dto.login.LoginDTO;
import com.madirex.gameserver.dto.user.LoginUserDTO;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.LoginMapper;
import com.madirex.gameserver.model.Login;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.LoginRepository;
import com.madirex.gameserver.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerMockTest {
    @MockBean
    private final LoginRepository loginRepository;
    @MockBean
    private final LoginMapper loginMapper;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }
    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    private final Login login = Login.builder()
            .user(user)
            .token(APIConfig.TEST_TOKEN)
            .instant(Date.from(Instant.now()))
            .build();
    @InjectMocks
    private LoginController loginController;
    @Autowired
    public LoginControllerMockTest(LoginRepository loginRepository, LoginMapper loginMapper) {
        this.loginRepository = loginRepository;
        this.loginMapper = loginMapper;
    }

    @Test
    @Order(1)
    void getAllTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instant(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.findAll())
                .thenReturn(List.of(login));

        Mockito.when(loginMapper.toDTO(List.of(login))).thenReturn(List.of(dto));

        var response = loginController.findAll();
        var res = response.getBody();
        assert res != null;
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.get(0).getInstant(), login.getInstant()),
                () -> assertEquals(res.get(0).getToken(), login.getToken())
        );
    }

    @Test
    @Order(2)
    void getByIdTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instant(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(Optional.of(login));

        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);

        var response = loginController.findById(login.getId());
        var res = response.getBody();
        assert res != null;
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstant(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1)).findById(login.getId());
        Mockito.verify(loginMapper, Mockito.times(1)).toDTO(login);
    }

    @Test
    @Order(3)
    void findByIdException() {
        Mockito.when(loginRepository.findById(login.getId())).thenReturn(Optional.empty());

        Exception ex = assertThrows(GeneralNotFoundException.class, () -> {
            loginController.findById(login.getId());
        });

        assertTrue(ex.getMessage().contains("login"));

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
    }

    @Test
    @Order(4)
    void saveTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instant(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.save(login))
                .thenReturn(login);

        Mockito.when(loginMapper.fromDTO(dto))
                .thenReturn(login);

        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);

        var response = loginController.save(dto);
        var res = response.getBody();
        assert res != null;
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstant(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .save(login);
        Mockito.verify(loginMapper, Mockito.times(1))
                .fromDTO(dto);
        Mockito.verify(loginMapper, Mockito.times(1))
                .toDTO(login);
    }

    @Test
    @Order(4)
    void updateTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instant(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(java.util.Optional.of(login));

        Mockito.when(loginRepository.save(login))
                .thenReturn(login);

        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);

        var response = loginController.update(login.getId(), login);
        var res = response.getBody();

        assert res != null;
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstant(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
        Mockito.verify(loginRepository, Mockito.times(1))
                .save(login);
        Mockito.verify(loginMapper, Mockito.times(1))
                .toDTO(login);
    }

    @Test
    @Order(5)
    void deleteTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instant(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(java.util.Optional.of(login));

        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);

        var response = loginController.delete(login.getId());
        var res = response.getBody();

        assert res != null;
        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstant(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
        Mockito.verify(loginMapper, Mockito.times(1))
                .toDTO(login);
    }
}