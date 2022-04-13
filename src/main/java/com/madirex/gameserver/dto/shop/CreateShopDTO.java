package com.madirex.gameserver.dto.shop;

import com.madirex.gameserver.model.Item;
import com.madirex.gameserver.model.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateShopDTO {
    private String shopName;
    private List<Item> items;
}
