package com.madirex.gameserver.services.users;

import com.madirex.gameserver.dto.user.CreateUserDTO;
import com.madirex.gameserver.dto.user.UserModifyDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.model.UserRole;
import com.madirex.gameserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Encontrar al usuario por nombre
     * @param username nombre de usuario
     * @return Optional de User
     */
    public Optional<User> findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    /**
     * Encontrar usuarios por nombre
     * @param username nombre de usuario
     * @return Lista de User
     */
    public List<User> findByUsernameContainsIgnoreCase(String username) {
        return userRepository.findByUsernameContainsIgnoreCase(username);
    }

    /**
     * Encontrar todos los usuarios
     * @return Lista de usuarios
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Buscar usuario por ID
     * @param id ID de usuario
     * @return Optional de usuario
     */
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Eliminar un usuario
     * @param user Usuario a eliminar
     * @return Usuario eliminado
     */
    public User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }

    /**
     * Encontrar usuario por email
     * @param email email del usuario
     * @return Usuario encontrado
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Buscar usuario por id
     * @param user Id del usuario
     * @return Optional de User
     */
    public Optional<User> findUserById(String user) {
        return userRepository.findById(user);
    }

    /**
     * Crear usuario
     * @param newUser CreateUserDTO (crear nuevo usuario)
     * @return Usuario creado
     */
    public User save(CreateUserDTO newUser) {
        if (newUser.getPassword().contentEquals(newUser.getPasswordConfirm())) {
            Set<UserRole> defaultRoles = new HashSet<>();
            defaultRoles.add(UserRole.PLAYER);
            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .username(newUser.getUsername())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .email(newUser.getEmail())
                    .roles(defaultRoles)
                    .money(0)
                    .build();
            try {
                return userRepository.save(user);
            } catch (DataIntegrityViolationException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del usuario o correo electrónico ya existe.");
            }
        } else {
            throw new GeneralBadRequestException("Contraseñas", "Las contraseñas no coinciden");
        }
    }

    /**
     * Actualizar un usuario
     * @param userModifyDTO UserModifyDTO - Modificar usuario
     * @param user User a modificar
     * @return Usuario actualizado
     */
    public User updateUser(UserModifyDTO userModifyDTO, User user) {
        String username = userModifyDTO.getUsername();
        String password = userModifyDTO.getPassword();
        String passwordConfirm = userModifyDTO.getPasswordConfirm();
        String email = userModifyDTO.getEmail();

        if (username != null) {
            user.setUsername(username);
        }
        if (password != null && passwordConfirm != null) {
            if (password.equals(passwordConfirm)) {
                user.setPassword(passwordEncoder.encode(password));
            }
        }
        if (email != null) {
            user.setEmail(email);
        }

        return userRepository.save(user);
    }
}
