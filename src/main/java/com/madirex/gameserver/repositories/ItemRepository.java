package com.madirex.gameserver.repositories;

import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, String> {

}
