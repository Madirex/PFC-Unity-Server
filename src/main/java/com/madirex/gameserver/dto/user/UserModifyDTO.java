package com.madirex.gameserver.dto.user;

import com.madirex.gameserver.dto.items.ItemDTO;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModifyDTO {
    private String username;
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;
    private String passwordConfirm;
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    private String email;
    private Integer money;
}
