package com.madirex.gameserver.dto.score;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
    private String id;
    //@NotBlank(message = "El usuario no puede ser vacío")
    //private User user;
    @NotNull(message = "El nivel no puede ser nulo")
    private int level;
    @NotNull(message = "La cantidad no puede ser nula")
    private int amount;
    @NotNull(message = "La fecha no puede ser nula")
    private LocalDateTime date;
}
