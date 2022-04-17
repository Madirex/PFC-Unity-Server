package com.madirex.gameserver.dto.score;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateScoreDTO {
    @NotNull(message = "El nivel no puede ser nulo")
    private int level;
    @NotNull(message = "La cantidad no puede ser nula")
    private int amount;
}
