package com.madirex.gameserver.services.scores;

import com.madirex.gameserver.dto.score.CreateScoreDTO;
import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.repositories.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public List<Score> findAll() {
        return scoreRepository.findAll();
    }

    public Optional<Score> findById(String id) {
        return scoreRepository.findById(id);
    }

    public Optional<Score> findScoreById(String score) {
        return scoreRepository.findById(score);
    }

    public Score save(CreateScoreDTO newScore) {
        Score score = new Score(newScore.getUser(), newScore.getLevel(), newScore.getAmount(), newScore.getDate());
        try {
            return scoreRepository.save(score);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El score ya existe");
        }
    }
}
