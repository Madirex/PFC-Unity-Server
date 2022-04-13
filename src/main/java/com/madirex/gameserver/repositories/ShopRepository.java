package com.madirex.gameserver.repositories;

import com.madirex.gameserver.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, String> {
}