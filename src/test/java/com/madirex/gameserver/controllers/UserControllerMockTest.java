package com.madirex.gameserver.controllers;

import com.madirex.gameserver.config.security.jwt.model.LoginRequest;
import com.madirex.gameserver.dto.user.CreateUserDTO;
import com.madirex.gameserver.dto.user.UserDTO;
import com.madirex.gameserver.dto.user.UserModifyDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.UserMapper;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.UserRepository;
import com.madirex.gameserver.services.users.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerMockTest {

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Autowired
    public UserControllerMockTest(UserService userService, UserMapper userMapper, UserRepository userRepository) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120007")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .password("test")
            .build();

    UserDTO userDTO = UserDTO.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4ffg")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .build();

    User user1 = User.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .build();

    User userCreate = User.builder()
            .id("ec272c62-9d31-42op-c239-0242ac120009")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .build();

    UserDTO userDTO1 = UserDTO.builder()
            .id("e9cb4fa0-0b77-4665-957b-d52d33123fda")
            .username("nombre usuario")
            .email("adsada@sdasdd.com")
            .build();

    @BeforeEach
    void setUp() {
        userRepository.save(user1);
    }

    @Test
    @Order(1)
    void findAll() {
        Mockito.when(userService.findAll()).thenReturn(List.of(user1));

        Mockito.when(userMapper.toDTO(List.of(user1))).thenReturn(List.of(userDTO1));
        assertEquals(
                userController.findAll(Optional.empty())
                , ResponseEntity.ok(List.of(userDTO1)));

        assertEquals(
                userController.findAll(Optional.of("test")).getStatusCodeValue()
                , 200);

        assertEquals(
                userController.findAll(Optional.empty()).getStatusCodeValue()
                , 200);

        assertEquals(
                userController.findAll(Optional.of("test")).getStatusCodeValue()
                , 200);

    }

    @Test
    @Order(2)
    void findAllFiltered() {
        Mockito.when(userService.findAll()).thenReturn(List.of(user1));
        Mockito.when(userMapper.toDTO(List.of(user1))).thenReturn(List.of(userDTO1));
        assertAll(
                () -> assertEquals(
                        ResponseEntity.ok(List.of(userDTO1))
                        , userController.findAll(Optional.empty())
                )
        );

        Mockito.verify(userMapper, Mockito.times(1)).toDTO(List.of(user1));
    }

    @Test
    @Order(3)
    void findById() {
        Mockito.when(userService.findUserById(user1.getId())).thenReturn(Optional.of(user1));
        Mockito.when(userMapper.toDTO(user1)).thenReturn(userDTO1);
        Mockito.when(userService.findUserById("notanID")).thenReturn(Optional.empty());

        assertThrows(GeneralNotFoundException.class, () -> {
            userController.findById("notanID");
        });
    }

    @Test
    void findByUsername() {
        Mockito.when(userMapper.toDTO(user1)).thenReturn(userDTO1);

        assertThrows(GeneralNotFoundException.class, () -> {
            userController.findByUsername("notanUsername");
        });
    }

    @Test
    void findByEmail() {
        Mockito.when(userMapper.toDTO(user1)).thenReturn(userDTO1);

        assertThrows(GeneralNotFoundException.class, () -> {
            userController.findByEmail("notanEmail");
        });
    }

    @Test
    @Order(4)
    void save() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername(userDTO1.getUsername());
        createUserDTO.setEmail(userDTO1.getEmail());

        User user = User.builder()
                .username(userDTO1.getUsername())
                .email(userDTO1.getEmail())
                .build();

        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        Mockito.when(userMapper.toDTO(user))
                .thenReturn(userDTO1);

        var response = userController.me(user);
        assert response != null;

        assertEquals(user.getUsername(), response.getUsername());
    }

    @Test
    void login() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername(userDTO1.getUsername());
        createUserDTO.setEmail(userDTO1.getEmail());

        User user = User.builder()
                .username(userDTO1.getUsername())
                .email(userDTO1.getEmail())
                .build();

        Mockito.when(userMapper.toDTO(user))
                .thenReturn(userDTO1);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("testPassword");
        loginRequest.setUsername("testName");

        assertThrows(NullPointerException.class, () -> {
            userController.login(loginRequest);
        });

    }

    @Test
    void newUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername(userDTO1.getUsername());
        createUserDTO.setEmail(userDTO1.getEmail());

        User user = User.builder()
                .username(userDTO1.getUsername())
                .email(userDTO1.getEmail())
                .build();

        Mockito.when(userMapper.toDTO(user))
                .thenReturn(userDTO1);

        createUserDTO.setPassword("sort");
        assertThrows(GeneralBadRequestException.class, () -> {
            userController.newUser(createUserDTO);
        });

        createUserDTO.setPasswordConfirm("notequals");
        assertThrows(GeneralBadRequestException.class, () -> {
            userController.newUser(createUserDTO);
        });

        createUserDTO.setPasswordConfirm("notregexmail");
        createUserDTO.setPassword("notregexmail");
        createUserDTO.setEmail("badmail");
        assertThrows(GeneralBadRequestException.class, () -> {
            userController.newUser(createUserDTO);
        });

        createUserDTO.setPasswordConfirm("3434gwe4h64FD5.-");
        createUserDTO.setPassword("3434gwe4h64FD5.-");
        createUserDTO.setEmail(userDTO1.getEmail());
        assertEquals(null, userController.newUser(createUserDTO));

    }

    @Test
    @Order(5)
    void update() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername(userDTO1.getUsername());

        UserModifyDTO userModifyDTO = new UserModifyDTO();
        userModifyDTO.setEmail("tested9t@dfsdf.com");

        Mockito.when(userMapper.toDTO(user1)).thenReturn(userDTO1);
        assertEquals(200, userController.mePut(user,userModifyDTO).getStatusCodeValue());

        Mockito.when(userMapper.toDTO(user1)).thenReturn(userDTO1);
    }

    @Test
    @Order(6)
    void delete() {
        Mockito.when(userService.deleteUser(any(User.class))).thenReturn(user1);
        Mockito.when(userService.findUserById(user1.getId())).thenReturn(Optional.of(user1));
        Mockito.when(userMapper.toDTO(user1)).thenReturn(userDTO1);

        assertEquals(userController.delete(user1.getId()), ResponseEntity.ok(user1));

        Mockito.verify(userService, Mockito.times(1)).findUserById(user1.getId());
        Mockito.verify(userService, Mockito.times(1)).deleteUser(any(User.class));

        assertThrows(GeneralNotFoundException.class, () -> {
            userController.delete("notanID");
        });
    }
}