package com.madirex.gameserver.config.security.jwt.model;

import com.madirex.gameserver.dto.user.UserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends UserDTO {
    @NotNull(message = "El token no puede ser nulo")
    private String token;

    /**
     * Builder de la respuesta JWT del usuario
     * @param id ID
     * @param username usuario
     * @param email email
     * @param userRoles roles de usuario
     * @param token token
     */
    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String id, String username,String email, Set<String> userRoles, String token) {
        super(id, username, email, userRoles, null, null);
        this.token = token;
    }
}
