package com.madirex.gameserver.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "El id no puede estar vacío")
    @NotNull(message = "El id no puede ser nulo")
    private String id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @NotNull(message = "El nombre no puede ser nulo")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "La confirmación de la contraseña no puede estar vacía")
    @NotNull(message = "La confirmación de la contraseña no puede ser nula")
    private String passwordConfirm;

    @NotBlank(message = "El email no puede estar vacío")
    @NotNull(message = "El email no puede ser nulo")
    @Email(regexp = ".*@.*\\..*", message = "El email debe ser válido")
    private String email;

    public CreateUserDTO(String username, String password, String passwordConfirm, String email) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }
}
