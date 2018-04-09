package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.models.binding.ItemBindingModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {

    List<ItemViewModel> findAllItems();

    String save(ItemBindingModel item, MultipartFile image) throws IOException;

    ItemViewModel getItemById(String id);

    String updateItem(String id, ItemBindingModel item, MultipartFile newImage) throws IOException;
}
