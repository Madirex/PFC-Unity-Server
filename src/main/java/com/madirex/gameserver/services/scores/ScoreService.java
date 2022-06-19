package com.madirex.gameserver.services.scores;

import com.madirex.gameserver.dto.score.CreateScoreDTO;
import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ScoreRepository;
import com.madirex.gameserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    /**
     * Encuentra todas las puntuaciones
     * @return lista de puntuaciones
     */
    public List<Score> findAll() {
        return scoreRepository.findAll();
    }

    /**
     * Encuentra todas las puntuaciones de un nivel ordenada por cantidad
     * @param level nivel
     * @return lista de puntuaciones
     */
    public List<Score> findAllByLevelOrderByAmountDesc(Integer level) {
        return scoreRepository.findAllByLevelOrderByAmountDesc(level);
    }

    /**
     * Encontrar todas las puntuaciones por nivel y usuario ordenadas por nivel
     * @param level nivel
     * @param user usuario
     * @return lista de puntuaciones
     */
    public List<Score> findAllByLevelAndUserOrderByLevel(String level, String user) {
        return scoreRepository.findAllByLevelAndUserOrderByLevel(level, user);
    }

    /**
     * Encontrar todas las puntuaciones de un usuario, ordenado por niveles
     * @param user Usuario
     * @return Lista de scores del usuario
     */
    public List<Score> findAllByUserOrderByLevel(String user) {
        return scoreRepository.findAllByUserOrderByLevel(user);
    }

    /**
     * Encontrar puntuación por ID
     * @param id ID de la puntuación
     * @return Optional de la puntuación
     */
    public Optional<Score> findById(String id) {
        return scoreRepository.findById(id);
    }

    /**
     * Encontrar puntuación por ID
     * @param score ID de la puntuación
     * @return Optional de la puntuación
     */
    public Optional<Score> findScoreById(String score) {
        return scoreRepository.findById(score);
    }

    /**
     * Crear una puntuación
     * @param createScoreDTO puntuación a crear
     * @param user usuario que genera la puntuación
     * @return Score (puntuación)
     */
    public Score createScore(CreateScoreDTO createScoreDTO, User user) {
        Score score = new Score(user, createScoreDTO.getLevel(), createScoreDTO.getAmount(), LocalDateTime.now());
        user.setMoney(user.getMoney() + score.getAmount());
        userRepository.save(user);
        return scoreRepository.save(score);
    }
}
