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

    /**
     * Buscar todos los ítems
     * @return Lista de todos los ítems
     */
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    /**
     * Buscar ítem por ID
     * @param id ID del ítem a buscar
     * @return Optional del ítem encontrado
     */
    public Optional<Item> findById(String id) {
        return itemRepository.findById(id);
    }

    /**
     * Encuentra un ítem dada un ID
     * @param item ID del ítem
     * @return Optional del ítem encontrado
     */
    public Optional<Item> findItemById(String item) {
        return itemRepository.findById(item);
    }

    /**
     * Elimina un ítem
     * @param item ítem a eliminar
     * @return ítem eliminado
     */
    public Item deleteItem(Item item) {
        itemRepository.delete(item);
        return item;
    }

    /**
     * Crea un ítem
     * @param createItemDTO ítem a crear
     * @return ítem creado
     */
    public Item createItem(CreateItemDTO createItemDTO) {
        Optional<Shop> shopOpt = shopRepository.findById(createItemDTO.getShop());
        return shopOpt.map(shop -> itemRepository.save(new Item(null, shop, createItemDTO.getName(),
                createItemDTO.getPrice(), createItemDTO.getItemType(), createItemDTO.getAmountPower()))).orElse(null);
    }

    /**
     * Actualizar ítem
     * @param updateItemDTO Datos nuevos del ítem
     * @param id ID del ítem a actualizar
     * @return ítem actualizado
     */
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

    /**
     * Comprar ítem
     * @param itemId ID del ítem
     * @param userData Datos del usuario
     * @return Item comprado
     */
    public Item buyItem(String itemId, User userData) {
        Optional<Item> itemOpt = itemRepository.findById(itemId);
        Optional<User> userOpt = userRepository.findById(userData.getId());
        if (itemOpt.isPresent() && userOpt.isPresent()){
            Item item = itemOpt.get();
            if (userData.getMoney() >= item.getPrice() && item.getShop() != null){
                User user = userOpt.get();
                item.setShop(null);
                item.setUser(user);
                user.setMoney(user.getMoney() - item.getPrice());
                itemRepository.save(itemOpt.get());
                userRepository.save(user);
                return item;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}