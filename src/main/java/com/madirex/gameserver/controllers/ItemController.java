package com.madirex.gameserver.controllers;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.dto.items.CreateItemDTO;
import com.madirex.gameserver.dto.items.ItemDTO;
import com.madirex.gameserver.dto.items.UpdateItemDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ItemMapper;
import com.madirex.gameserver.mapper.LoginMapper;
import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ItemRepository;
import com.madirex.gameserver.repositories.LoginRepository;
import com.madirex.gameserver.services.items.ItemService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/item")

public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemController(ItemService itemService, ItemMapper itemMapper, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.itemRepository = itemRepository;
    }

    @ApiOperation(value = "Obtener todos los ítems", notes = "Obtiene todos los ítems")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ItemDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<List<ItemDTO>> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery
    ) {
        List<Item> items;
            items = itemService.findAll();
            return ResponseEntity.ok(itemMapper.toDTO(items));
    }

    @ApiOperation(value = "Obtener un ítem por id", notes = "Obtiene un ítem en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ItemDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> findById(@PathVariable String id) {

        Item item = itemService.findById(id).orElse(null);
        if (item == null) {
            throw new GeneralNotFoundException(id, "No se ha encontrado el ítem con la id solicitada");
        } else {
            return ResponseEntity.ok(itemMapper.toDTO(item));
        }
    }

    @ApiOperation(value = "Actualizar un ítem", notes = "Actualiza un ítem en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UpdateItemDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> update(@PathVariable String id, @RequestBody UpdateItemDTO updateItemDTO) {
        Item updated = itemService.findItemById(id).orElse(null);
        if (updated == null) {
            throw new GeneralNotFoundException("Actualizar Item", "Error al actualizar el ítem.");
        } else {
            Item created = itemService.updateItem(updateItemDTO, updated.getId());
            return ResponseEntity.ok(itemMapper.toDTO(created));
        }
    }

    @ApiOperation(value = "Comprar el ítem", notes = "Opción de compra del ítem para el jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ItemDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @PutMapping("/buy/{id}")
    public ResponseEntity<ItemDTO> buyItem(@AuthenticationPrincipal User user, @PathVariable String id) {
        try {
            Item created = itemService.buyItem(id, user);
            return ResponseEntity.ok(itemMapper.toDTO(created));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Comprar ítem", "Error de usuario al intentar comprar un ítem: " + e.getMessage());
        }
    }

    @ApiOperation(value = "Crear un ítem", notes = "Crea un ítem")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = CreateItemDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ResponseEntity<ItemDTO> save(@RequestBody CreateItemDTO createItemDTO) {
            if (createItemDTO.getShop() == null || createItemDTO.getName() == null || createItemDTO.getItemType() == null) {
                throw new GeneralBadRequestException("Insertar Item", "Error al insertar el ítem: La tienda, el nombre y el tipo del ítem deben complementarse.");
            }
            Item inserted = itemService.createItem(createItemDTO);
            return ResponseEntity.ok(itemMapper.toDTO(inserted));
    }

    @ApiOperation(value = "Eliminar un ítem", notes = "Elimina un ítem en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ItemDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ItemDTO> delete(@PathVariable String id) {
            Item item = itemService.findItemById(id).orElse(null);
            if (item == null) {
                throw new GeneralNotFoundException("id: " + id, "error al intentar borrar el ítem con id " + id);
            } else {
                itemService.deleteItem(item);
                return ResponseEntity.ok(itemMapper.toDTO(item));
            }
    }
}