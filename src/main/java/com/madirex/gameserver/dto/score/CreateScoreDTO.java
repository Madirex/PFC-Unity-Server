package com.madirex.gameserver.dto.score;

import com.madirex.gameserver.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateScoreDTO {
    private User user;
    private int level;
    private int amount;
    private LocalDateTime date = LocalDateTime.now();
}
