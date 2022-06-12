package com.madirex.gameserver.services.shops;

import com.madirex.gameserver.dto.shop.CreateShopDTO;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    /**
     * Encontrar todas las tiendas
     * @return Lista de tiendas
     */
    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    /**
     * Encontrar la tienda por ID
     * @param id ID de la tienda
     * @return Optional de la tienda
     */
    public Optional<Shop> findById(String id) {
        return shopRepository.findById(id);
    }

    /**
     * Encontrar la tienda por ID
     * @param shop ID de la tienda
     * @return Optional de la tienda encontrada
     */
    public Optional<Shop> findShopById(String shop) {
        return shopRepository.findById(shop);
    }

    /**
     * Eliminar una tienda
     * @param shop tienda a eliminar
     * @return Tienda eliminada
     */
    public Shop deleteShop(Shop shop) {
        shopRepository.delete(shop);
        return shop;
    }

    /**
     * Crear una tienda
     * @param createShopDTO CreateShopDTO (crear tienda)
     * @return Tienda creada
     */
    public Shop createShop(CreateShopDTO createShopDTO) {
        return shopRepository.save(new Shop(createShopDTO.getShopName()));
    }

    /**
     * Actualizar tienda
     * @param createShopDTO CreateShopDTO (crear tienda)
     * @param id ID de la tienda a actualizar
     * @return Shop (tienda)
     */
    public Shop updateShop(CreateShopDTO createShopDTO, String id) {
        Shop shop = new Shop(createShopDTO.getShopName());
        shop.setId(id);
        return shopRepository.save(shop);
    }
}
