package com.madirex.gameserver.services.shops;

import com.madirex.gameserver.dto.items.CreateItemDTO;
import com.madirex.gameserver.dto.shop.CreateShopDTO;
import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public Optional<Shop> findById(String id) {
        return shopRepository.findById(id);
    }

    public Optional<Shop> findShopById(String shop) {
        return shopRepository.findById(shop);
    }

    public Shop deleteShop(Shop shop) {
        shopRepository.delete(shop);
        return shop;
    }

    public Shop createShop(CreateShopDTO createShopDTO) {
        return shopRepository.save(new Shop(createShopDTO.getShopName()));
    }

    public Shop updateShop(CreateShopDTO createShopDTO, String id) {
        Shop shop = new Shop(createShopDTO.getShopName());
        shop.setId(id);
        return shopRepository.save(shop);
    }
}
