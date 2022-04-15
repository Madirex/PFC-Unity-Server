package com.madirex.gameserver.services.scores;

import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.repositories.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public List<Score> findAll() {
        return scoreRepository.findAll();
    }

    public List<Score> findAllByLevelOrderByAmountDesc(Integer level){return scoreRepository.findAllByLevelOrderByAmountDesc(level);}

    public List<Score> findAllByLevelAndUserOrderByLevel(String level, String user){return scoreRepository.findAllByLevelAndUserOrderByLevel(level, user);}

    public List<Score> findAllByUserOrderByLevel(String user){return scoreRepository.findAllByUserOrderByLevel(user);}

    public Optional<Score> findById(String id) {
        return scoreRepository.findById(id);
    }

    public Optional<Score> findScoreById(String score) {
        return scoreRepository.findById(score);
    }
}
