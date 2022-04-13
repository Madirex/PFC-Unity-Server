package com.madirex.gameserver.repositories;

import com.madirex.gameserver.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, String> {
    Optional<Shop> findByNameIgnoreCase(String shopName);

    List<Shop> findByNameContainsIgnoreCase(String shopName);
}
