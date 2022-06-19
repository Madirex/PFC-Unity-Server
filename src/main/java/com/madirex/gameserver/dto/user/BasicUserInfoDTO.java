package com.madirex.gameserver.dto.user;

import com.madirex.gameserver.dto.items.ItemDTO;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicUserInfoDTO {
    @NotBlank(message = "El id no puede estar vacío")
    private String id;
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    private String email;
}
