package com.madirex.gameserver.controllers;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.dto.items.CreateItemDTO;
import com.madirex.gameserver.dto.items.ItemDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ItemMapper;
import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.repositories.ItemRepository;
import com.madirex.gameserver.services.items.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    @ApiOperation(value = "Obtener todos los ítems", notes = "Obtiene todos los ítems")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ItemDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery
    ) {
        List<Item> items;
        try {
            items = itemService.findAll();
            return ResponseEntity.ok(itemMapper.toDTO(items));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @ApiOperation(value = "Obtener un ítem por id", notes = "Obtiene un ítem en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ItemDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Item item = itemService.findById(id).orElse(null);
        if (item == null) {
            throw new GeneralNotFoundException(id, "No se ha encontrado el ítem con la id solicitada");
        } else {
            return ResponseEntity.ok(itemMapper.toDTO(item));
        }
    }

    @ApiOperation(value = "Crear un ítem", notes = "Crea un ítem")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = ItemDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ItemDTO newItem(@RequestBody CreateItemDTO newItem) {
        return itemMapper.toDTO(itemService.save(newItem));
    }
}