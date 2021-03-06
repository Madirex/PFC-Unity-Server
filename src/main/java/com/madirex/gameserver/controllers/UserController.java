package com.madirex.gameserver.controllers;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.config.security.jwt.JwtTokenProvider;
import com.madirex.gameserver.config.security.jwt.model.JwtUserResponse;
import com.madirex.gameserver.config.security.jwt.model.LoginRequest;
import com.madirex.gameserver.dto.shop.ShopDTO;
import com.madirex.gameserver.dto.user.CreateUserDTO;
import com.madirex.gameserver.dto.user.UserDTO;
import com.madirex.gameserver.dto.user.UserModifyDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.UserMapper;
import com.madirex.gameserver.model.Login;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.model.UserRole;
import com.madirex.gameserver.repositories.LoginRepository;
import com.madirex.gameserver.services.users.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping(APIConfig.API_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginRepository loginRepository;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    /**
     * Obtener todos los usuarios
     * @param searchQuery Consulta de b??squeda
     * @return Respuesta - Lista de User DTO
     */
    @ApiOperation(value = "Obtener todos los usuarios", notes = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery
    ) {
        List<User> users;
        if (searchQuery.isPresent())
            users = userService.findByUsernameContainsIgnoreCase(searchQuery.get());
        else
            users = userService.findAll();
        return ResponseEntity.ok(userMapper.toDTO(users));
    }

    /**
     * Obtener un usuario por ID
     * @param id ID
     * @return Respuesta - Usuario DTO
     */
    @ApiOperation(value = "Obtener un usuario por id", notes = "Obtiene un usuario en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            throw new GeneralNotFoundException(id, "No se ha encontrado el usuario con la id solicitada");
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    /**
     * Obtener un usuario por nombre de usuario
     * @param username nombre de usuario
     * @return Respuesta - User DTO
     */
    @ApiOperation(value = "Obtener un usuario por username", notes = "Obtiene un usuario en base al username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/name/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        User user = userService.findByUsernameIgnoreCase(username).orElse(null);
        if (user == null) {
            throw new GeneralNotFoundException(username, "No se ha encontrado el usuario con el username solicitado");
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    /**
     * Obtener un usuario por email
     * @param email email
     * @return Respuesta - Usuario DTO
     */
    @ApiOperation(value = "Obtener un usuario por email", notes = "Obtiene un usuario en base al email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new GeneralNotFoundException(email, "No se ha encontrado el usuario con el email solicitado");
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    /**
     * Obtener un usuario
     * @param user usuario
     * @return usuario DTO
     */
    @ApiOperation(value = "Obtener un usuario", notes = "Obtiene un usuario que esta logueado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal User user) {
        return userMapper.toDTO(user);
    }

    /**
     * Actualizar usuario
     * @param user usuario a actualizar
     * @param userModifyDTO datos nuevos
     * @return Respuesta - Usuario DTO
     */
    @ApiOperation(value = "Actualizar usuario", notes = "Actualiza el usuario logueado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserModifyDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @PutMapping("/me")
    public ResponseEntity<UserDTO> mePut(@AuthenticationPrincipal User user, @RequestBody UserModifyDTO userModifyDTO) {
            User created = userService.updateUser(userModifyDTO, user);
            return ResponseEntity.ok(userMapper.toDTO(created));
    }

    /**
     * Loguear un usuario
     * @param loginRequest Petici??n de usuario
     * @return JwtUserResponse
     */
    @ApiOperation(value = "Loguear un usuario", notes = "Loguea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping( "/login")
    public JwtUserResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwtToken = tokenProvider.generateToken(authentication);

        Login login = new Login(jwtToken, Date.from(Instant.now()), user);
        loginRepository.save(login);
        return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);
    }

    /**
     * Crear un usuario
     * @param newUser nuevo usuario
     * @return DTO de usuario
     */
    @ApiOperation(value = "Crear un usuario", notes = "Crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public UserDTO newUser(@RequestBody CreateUserDTO newUser) {
        if (newUser.getPassword().length() < 5){
            throw new GeneralBadRequestException("Insertar", "Error al insertar registro - Las contrase??a no puede tener menos de 5 caracteres");
        }

        if (!newUser.getPassword().equals(newUser.getPasswordConfirm())){
            throw new GeneralBadRequestException("Insertar", "Error al insertar registro - Las contrase??as no coinciden");
        }

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(newUser.getEmail());
        if (!matcher.matches()){
            throw new GeneralBadRequestException("Insertar", "Error al insertar registro - El email introducido no tiene formato v??lido");
        }

        return userMapper.toDTO(userService.save(newUser));
    }

    /**
     * Eliminar un usuario
     * @param id ID
     * @return Respuesta - Usuario
     */
    @ApiOperation(value = "Eliminar un usuario", notes = "Elimina un usuario en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable String id) {
            User user = userService.findUserById(id).orElse(null);
            if (user == null) {
                throw new GeneralNotFoundException("id: " + id, "error al intentar borrar el usuario con el id " + id);
            } else {
                User userDone = userService.deleteUser(user);
                return ResponseEntity.ok(userDone);
            }
    }

    /**
     * Convertir entidad de usuario y token a JWT respuesta de usuario
     * @param user usuario
     * @param jwtToken Token JWT
     * @return JWTUserResponse
     */
    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(User user, String jwtToken) {
        return JwtUserResponse
                .jwtUserResponseBuilder()
                .id(user.getId())
                .email(user.getEmail())
                .userRoles(user.getRoles().stream().map(UserRole::name).collect(Collectors.toSet()))
                .username(user.getUsername())
                .token(jwtToken)
                .build();
    }
}