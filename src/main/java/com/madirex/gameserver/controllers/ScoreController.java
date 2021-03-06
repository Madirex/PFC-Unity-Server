package com.madirex.gameserver.controllers;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.dto.score.CreateScoreDTO;
import com.madirex.gameserver.dto.score.ScoreDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ScoreMapper;
import com.madirex.gameserver.model.Score;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ScoreRepository;
import com.madirex.gameserver.services.scores.ScoreService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    /**
     * Obtener todas las puntuaciones
     * @param level nivel
     * @param user usuario
     * @return respuesta - lista de Score DTO
     */
    @ApiOperation(value = "Obtener todas las puntuaciones", notes = "Obtiene todas las puntuaciones")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ScoreDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<List<ScoreDTO>> findAll(
            @RequestParam("level") Optional<String> level,
            @RequestParam("user") Optional<String> user
    ) {
        List<Score> scores;
        try {
            if (level.isPresent()){
                try {
                    int num = Integer.parseInt(level.get());
                    if (user.isPresent()){
                        scores = scoreService.findAllByLevelAndUserOrderByLevel(level.get(), user.get());
                    }else{
                        scores = scoreService.findAllByLevelOrderByAmountDesc(num);
                    }
                } catch (final NumberFormatException e) {
                    throw new GeneralBadRequestException("Selecci??n de Datos", "el nivel introducido debe de ser un n??mero entero");
                }
            }else{
                if(user.isPresent()){
                    scores = scoreService.findAllByUserOrderByLevel(user.get());
                }else{
                    scores = scoreService.findAll();
                }
            }
            return ResponseEntity.ok(scoreMapper.toDTO(scores));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selecci??n de Datos", "Par??metros de consulta incorrectos");
        }
    }

    /**
     * Eliminar una puntuaci??n
     * @param id ID
     * @return respuesta - score DTO
     */
    @ApiOperation(value = "Eliminar un score", notes = "Elimina un score en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ScoreDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ScoreDTO> delete(@PathVariable String id) {
        Score score = scoreRepository.findById(id).orElse(null);
        if (score == null) {
            throw new GeneralNotFoundException(id,"No se ha encontrado el score con la id solicitada");
        }
        try {
            scoreRepository.delete(score);
            return ResponseEntity.ok(scoreMapper.toDTO(score));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el score");
        }
    }

    /**
     * Obtener una puntuaci??n por ID
     * @param id ID
     * @return respuesta - score DTO
     */
    @ApiOperation(value = "Obtener una puntuaci??n por id", notes = "Obtiene una puntuaci??n en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ScoreDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScoreDTO> findById(@PathVariable String id) {
        Score score = scoreService.findById(id).orElse(null);
        if (score == null) {
            throw new GeneralNotFoundException(id, "No se ha encontrado la puntuaci??n con la id solicitada");
        } else {
            return ResponseEntity.ok(scoreMapper.toDTO(score));
        }
    }

    /**
     * Crear puntuaci??n
     * @param user usuario que crea la puntuaci??n
     * @param createScoreDTO puntuaci??n a crear
     * @return respuesta - score DTO
     */
    @ApiOperation(value = "Crear score", notes = "Opci??n de crear score")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ScoreDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @PostMapping("/")
    public ResponseEntity<ScoreDTO> buyItem(@AuthenticationPrincipal User user, @RequestBody CreateScoreDTO createScoreDTO) {
        try {
            Score created = scoreService.createScore(createScoreDTO, user);
            return ResponseEntity.ok(scoreMapper.toDTO(created));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Crear score", "Error al crear Score: " + e.getMessage());
        }
    }

}