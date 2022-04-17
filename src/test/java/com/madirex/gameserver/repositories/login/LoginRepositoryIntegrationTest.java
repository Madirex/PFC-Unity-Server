package com.madirex.gameserver.repositories.login;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.model.Login;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.LoginRepository;
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
public class LoginRepositoryIntegrationTest {
    private final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    private final Login login = Login.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .user(user)
            .token(APIConfig.TEST_TOKEN)
            .instant(Date.from(Instant.now()))
            .build();
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @Order(1)
    void save() {
        Login res = loginRepository.save(login);

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant()),
                () -> assertEquals(login.getToken(), res.getToken())
        );
    }

    @Test
    @Order(2)
    void getAllLogin() {
        loginRepository.save(login);
        assertEquals(1, loginRepository.findAll().size());
    }

    @Test
    @Order(3)
    void getLoginById() {
        Login log = loginRepository.save(login);
        Optional<Login> loginOpt = loginRepository.findById(log.getId());
        assumeTrue(loginOpt.isPresent());
        Login res = loginOpt.get();

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant()),
                () -> assertEquals(login.getToken(), res.getToken())
        );
    }

    @Test
    @Order(4)
    void updateLogin() {
        Login logi = loginRepository.save(login);
        Optional<Login> loginOpt = loginRepository.findById(logi.getId());
        assumeTrue(loginOpt.isPresent());
        logi = loginOpt.get();
        logi.setToken(APIConfig.TEST_TOKEN);
        Login res = loginRepository.save(logi);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(APIConfig.TEST_TOKEN, res.getToken()),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant())
        );
    }

    @Test
    @Order(5)
    public void deleteLogin() {
        Login res = loginRepository.save(login);
        Optional<Login> loginOpt = loginRepository.findById(res.getId());
        assumeTrue(loginOpt.isPresent());
        res = loginOpt.get();
        loginRepository.delete(res);
        assertNull(loginRepository.findById(res.getId()).orElse(null));

    }
}