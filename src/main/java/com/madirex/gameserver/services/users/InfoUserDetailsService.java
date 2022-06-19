package com.madirex.gameserver.services.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class InfoUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * Cargar usuario por nombre de usuario
     * @param username nombre de usuario
     * @return UserDetails (detalles del usuario)
     * @throws UsernameNotFoundException Usuario no encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado"));
    }

    /**
     * Cargar usuario por ID
     * @param user ID del usuario
     * @return UserDetails (detalles del usuario)
     */
    public UserDetails loadUserById(String user) {
        return userService.findUserById(user)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con id: " + user + " no encontrado"));
    }
}
