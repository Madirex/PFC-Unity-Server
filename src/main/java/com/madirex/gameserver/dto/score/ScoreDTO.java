package com.madirex.gameserver.dto.score;

import com.madirex.gameserver.dto.user.BasicUserInfoDTO;
import com.madirex.gameserver.dto.user.UserDTO;
import com.madirex.gameserver.model.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
    private String id;
    @NotBlank(message = "El usuario no puede ser vacío")
    private BasicUserInfoDTO user;
    @NotNull(message = "El nivel no puede ser nulo")
    private int level;
    @NotNull(message = "La cantidad no puede ser nula")
    private int amount;
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime date;
}
