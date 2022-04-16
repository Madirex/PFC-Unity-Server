package com.madirex.gameserver.services.items;

import com.madirex.gameserver.dto.items.CreateItemDTO;
import com.madirex.gameserver.dto.items.UpdateItemDTO;
import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.Shop;
import com.madirex.gameserver.model.User;
import com.madirex.gameserver.repositories.ItemRepository;
import com.madirex.gameserver.repositories.ShopRepository;
import com.madirex.gameserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
    }

    public Optional<Item> findItemById(String item) {
        return itemRepository.findById(item);
    }

    public Item deleteItem(Item item) {
        itemRepository.delete(item);
        return item;
    }

    public Item createItem(CreateItemDTO createItemDTO) {
        Optional<Shop> shopOpt = shopRepository.findById(createItemDTO.getShop());
        return shopOpt.map(shop -> itemRepository.save(new Item(null, shop, createItemDTO.getName(),
                createItemDTO.getPrice(), createItemDTO.getItemType(), createItemDTO.getAmountPower()))).orElse(null);
    }

    public Item updateItem(UpdateItemDTO updateItemDTO, String id) {
        String shopId = updateItemDTO.getShop();
        String userId = updateItemDTO.getUser();
        Item item = new Item(null, null, updateItemDTO.getName(),
                updateItemDTO.getPrice(), updateItemDTO.getItemType(), updateItemDTO.getAmountPower());
        item.setId(id);
        if (shopId != null){
            Optional<Shop> shopOpt = shopRepository.findById(updateItemDTO.getShop());
            if (shopOpt.isPresent()){
                item.setShop(shopOpt.get());
            }else{
                return null;
            }
        } else if (userId != null){
            Optional<User> userOpt = userRepository.findById(updateItemDTO.getUser());
            if (userOpt.isPresent()){
                item.setUser(userOpt.get());
            }else{
                return null;
            }
        }else{
            return null;
        }
        return itemRepository.save(item);
    }
}
