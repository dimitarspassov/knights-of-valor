package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.models.binding.ItemBindingModel;
import com.dspassov.kovapi.areas.game.models.view.ItemPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import org.springframework.web.multipart.MultipartFile;


public interface ItemService {

    ItemPageViewModel findItemsByPage(int page, int size);

    ItemPageViewModel findItemsByPageRegardlessOfStatus(int page, int size);

    ItemPageViewModel findItemsByPageAndType(int page, int size, String type);

    ItemPageViewModel findItemsByPageAndName(int page, int size, String name);

    String save(ItemBindingModel item, MultipartFile image);

    ItemViewModel getItemById(String id);

    String updateItem(String id, ItemBindingModel item, MultipartFile newImage);

    String changeStatus(String itemId, boolean newStatus);
}
