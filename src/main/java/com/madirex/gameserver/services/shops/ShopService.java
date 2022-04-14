package com.madirex.gameserver.services.shops;

import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.repositories.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

}
