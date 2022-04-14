package com.madirex.gameserver.mapper;

import com.madirex.gameserver.dto.score.ScoreDTO;
import com.madirex.gameserver.model.Score;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScoreMapper {
    private final ModelMapper modelMapper;

    public ScoreDTO toDTO(Score score) {
        return modelMapper.map(score, ScoreDTO.class);
    }

    public List<ScoreDTO> toDTO(List<Score> scores) {
        return scores.stream().map(this::toDTO).collect(Collectors.toList());
    }
}