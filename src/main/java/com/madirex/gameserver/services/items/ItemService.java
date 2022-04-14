package com.madirex.gameserver.services.items;

import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
    }

    public Optional<Item> findItemById(String item) {
        return itemRepository.findById(item);
    }
}
