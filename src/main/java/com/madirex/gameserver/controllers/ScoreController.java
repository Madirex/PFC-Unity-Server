package com.madirex.gameserver.controllers;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.dto.score.CreateScoreDTO;
import com.madirex.gameserver.dto.score.ScoreDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ScoreMapper;
import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.repositories.ScoreRepository;
import com.madirex.gameserver.services.scores.ScoreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/score")
@RequiredArgsConstructor
public class ScoreController {
    private final ScoreService scoreService;
    private final ScoreMapper scoreMapper;
    private final ScoreRepository scoreRepository;

    @ApiOperation(value = "Obtener todas las puntuaciones", notes = "Obtiene todas las puntuaciones")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ScoreDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery //TODO: score de un jugador concreto
    ) {
        List<Score> scores;
        try {
            if (searchQuery.isPresent())
                scores = scoreService.findByNameContainsIgnoreCase(searchQuery.get());
            else
                scores = scoreService.findAll();
            return ResponseEntity.ok(scoreMapper.toDTO(scores));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @ApiOperation(value = "Obtener una puntuación por id", notes = "Obtiene una puntuación en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ScoreDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Score score = scoreService.findById(id).orElse(null);
        if (score == null) {
            throw new GeneralNotFoundException(id, "No se ha encontrado la puntuación con la id solicitada");
        } else {
            return ResponseEntity.ok(scoreMapper.toDTO(score));
        }
    }

    @ApiOperation(value = "Crear una puntuación", notes = "Crea una puntación")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = ScoreDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ScoreDTO newScore(@RequestBody CreateScoreDTO newScore) {
        return scoreMapper.toDTO(scoreService.save(newScore));
    }

}