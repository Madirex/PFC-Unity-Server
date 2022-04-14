package com.madirex.gameserver.controllers;

import com.madirex.gameserver.config.APIConfig;
import com.madirex.gameserver.dto.shop.ShopDTO;
import com.madirex.gameserver.exceptions.GeneralBadRequestException;
import com.madirex.gameserver.exceptions.GeneralNotFoundException;
import com.madirex.gameserver.mapper.ShopMapper;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.repositories.ShopRepository;
import com.madirex.gameserver.services.shops.ShopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;
    private final ShopMapper shopMapper;
    private final ShopRepository shopRepository;

    @ApiOperation(value = "Obtener todas las tiendas", notes = "Obtiene todas las tiendas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery
    ) {
        List<Shop> shops;
        try {
                shops = shopService.findAll();
            return ResponseEntity.ok(shopMapper.toDTO(shops));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @ApiOperation(value = "Obtener una tienda por id", notes = "Obtiene una tienda en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ShopDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Shop shop = shopService.findById(id).orElse(null);
        if (shop == null) {
            throw new GeneralNotFoundException(id, "No se ha encontrado la tienda con la id solicitada");
        } else {
            return ResponseEntity.ok(shopMapper.toDTO(shop));
        }
    }

}