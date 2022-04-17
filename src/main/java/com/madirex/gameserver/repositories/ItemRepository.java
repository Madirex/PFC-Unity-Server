package com.madirex.gameserver.repositories;

import com.madirex.gameserver.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {

}
