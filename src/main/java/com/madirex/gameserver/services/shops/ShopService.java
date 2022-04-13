package com.madirex.gameserver.services.shops;

import com.madirex.gameserver.dto.shop.CreateShopDTO;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.repositories.ShopRepository;
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
public class ShopService {
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Shop> findByNameIgnoreCase(String name) {
        return shopRepository.findByNameIgnoreCase(name);
    }

    public List<Shop> findByNameContainsIgnoreCase(String shopName) {
        return shopRepository.findByNameContainsIgnoreCase(shopName);
    }

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public Optional<Shop> findById(String id) {
        return shopRepository.findById(id);
    }

    public Optional<Shop> findShopById(String shop) {
        return shopRepository.findById(shop);
    }

    public Shop save(CreateShopDTO newShop) {
        Shop shop = new Shop(newShop.getShopName(), newShop.getItems());
        try {
            return shopRepository.save(shop);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El shop ya existe");
        }
    }
}
