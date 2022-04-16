package com.madirex.gameserver.services.users;
import com.madirex.gameserver.dto.user.CreateUserDTO;
import com.madirex.gameserver.dto.user.UserDTO;
import com.madirex.gameserver.dto.user.UserModifyDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.Shop;
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

    public Optional<User> findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public List<User> findByUsernameContainsIgnoreCase(String username) {
        return userRepository.findByUsernameContainsIgnoreCase(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User deleteUser(User user) {
        userRepository.delete(user);
        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(String user) {
        return userRepository.findById(user);
    }

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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del usuario ya existe");
            }
        } else {
            throw new GeneralBadRequestException("Contraseñas","Las contraseñas no coinciden");
        }
    }

    public User updateUser(UserModifyDTO userModifyDTO, User user) {
        String username = userModifyDTO.getUsername();
        String password = userModifyDTO.getPassword();
        String passwordConfirm = userModifyDTO.getPasswordConfirm();
        String email = userModifyDTO.getEmail();
        Integer money = userModifyDTO.getMoney();

        if (username != null){
            user.setUsername(username);
        }
        if (password != null && passwordConfirm != null) {
            if (password.equals(passwordConfirm)){
                user.setPassword(passwordEncoder.encode(password));
            }
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (money != null) {
            user.setMoney(money);
        }
        return userRepository.save(user);
    }
}
