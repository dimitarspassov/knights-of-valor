package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.common.Toolbox;
import com.dspassov.kovapi.areas.game.entities.Armor;
import com.dspassov.kovapi.areas.game.entities.Item;
import com.dspassov.kovapi.areas.game.entities.Shield;
import com.dspassov.kovapi.areas.game.entities.Weapon;
import com.dspassov.kovapi.areas.game.enumerations.ItemType;
import com.dspassov.kovapi.areas.game.models.binding.ItemBindingModel;
import com.dspassov.kovapi.areas.game.models.view.ItemPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import com.dspassov.kovapi.areas.cloud.CloudService;
import com.dspassov.kovapi.exceptions.CloudStorageException;
import com.dspassov.kovapi.exceptions.IllegalParamException;
import com.dspassov.kovapi.repositories.ItemRepository;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;
    private final CloudService cloudService;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ModelMapper modelMapper, CloudService cloudService) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
        this.cloudService = cloudService;
    }

    @Override
    public String save(ItemBindingModel itemModel, MultipartFile image) {

        String fileName = image.getOriginalFilename();
        if (!Toolbox.isValidImage(fileName)) {
            throw new IllegalParamException(ResponseMessageConstants.INCORRECT_FILE_EXTENSION);
        }

        if (this.itemRepository.findByName(itemModel.getName()) != null) {
            throw new IllegalParamException(ResponseMessageConstants.EXISTING_ITEM);
        }

        Item item;

        switch (itemModel.getType()) {
            case "Weapon": {
                item = this.modelMapper.map(itemModel, Weapon.class);
            }
            break;
            case "Shield": {
                item = this.modelMapper.map(itemModel, Shield.class);
            }
            break;
            case "Armor": {
                item = this.modelMapper.map(itemModel, Armor.class);
            }
            break;
            default:
                throw new IllegalParamException(ResponseMessageConstants.UNSUPPORTED_ITEM_TYPE);
        }


        String imageUrl;
        try {
            imageUrl = this.cloudService.saveImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CloudStorageException();
        }

        item.setImage(imageUrl);

        item.setStatus(true);
        this.itemRepository.save(item);

        return ResponseMessageConstants.ITEM_ADDED;
    }

    @Override
    public List<ItemViewModel> findAllItems() {
        return this.itemRepository.findAll()
                .stream()
                .map(i -> {
                    ItemViewModel viewModel = this.modelMapper.map(i, ItemViewModel.class);
                    viewModel.setType(i.getClass().getSimpleName());
                    return viewModel;
                }).collect(Collectors.toList());
    }

    public ItemPageViewModel findItemsByPage(int page, int size) {

        Page<Item> items = this.itemRepository.findAll(PageRequest.of(page, size));
        return this.mapItemsToPageViewModel(items);
    }

    @Override
    public ItemPageViewModel findItemsByPageAndType(int page, int size, String type) {

        try {
            ItemType itemType = ItemType.valueOf(type);
            Page<Item> items = this.itemRepository.findAllByType(itemType.name(), PageRequest.of(page, size));

            return this.mapItemsToPageViewModel(items);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public ItemPageViewModel findItemsByPageAndName(int page, int size, String name) {

        try {
            Page<Item> items = this.itemRepository.findAllByNameContaining(name, PageRequest.of(page, size));
            return this.mapItemsToPageViewModel(items);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public ItemViewModel getItemById(String id) {
        Item item = this.itemRepository.findById(id).orElse(null);

        if (item == null) {
            throw new IllegalParamException(ResponseMessageConstants.NON_EXISTENT_ITEM);
        }

        ItemViewModel model = this.modelMapper.map(item, ItemViewModel.class);
        model.setType(item.getClass().getSimpleName());
        return model;
    }


    @Override
    public String updateItem(String id, ItemBindingModel item, MultipartFile newImage) {

        Item current = this.itemRepository.findById(id).orElse(null);

        if (current == null) {
            throw new IllegalParamException(ResponseMessageConstants.NON_EXISTENT_ITEM);
        }

        if (!item.getName().equals(current.getName()) && this.itemRepository.findByName(item.getName()) != null) {
            throw new IllegalParamException(ResponseMessageConstants.EXISTING_ITEM);
        }


        current.setBonus(item.getBonus());
        current.setName(item.getName());
        current.setPrice(item.getPrice());

        if (newImage != null) {

            if (!Toolbox.isValidImage(newImage.getOriginalFilename())) {
                throw new IllegalParamException(ResponseMessageConstants.INCORRECT_FILE_EXTENSION);
            }

            try {
                this.cloudService.deleteImage(Toolbox.getImageId(current.getImage()));
                current.setImage(this.cloudService.saveImage(newImage));
            } catch (IOException e) {
                e.printStackTrace();
                throw new CloudStorageException();
            }

        }

        this.itemRepository.save(current);
        return ResponseMessageConstants.ITEM_EDITED;

    }

    @Override
    public String changeStatus(String itemId, boolean newStatus) {
        Item item = this.itemRepository.findById(itemId).orElse(null);

        if (item != null) {
            item.setStatus(newStatus);
            this.itemRepository.save(item);
        }

        return ResponseMessageConstants.ITEM_EDITED;
    }

    private ItemPageViewModel mapItemsToPageViewModel(Page<Item> items) {
        ItemPageViewModel itemPage = this.modelMapper.map(items, ItemPageViewModel.class);
        itemPage.setAllPages(items.getTotalPages());
        itemPage.setItems(items.getContent().stream().map(i -> {

            ItemViewModel itemViewModel = this.modelMapper.map(i, ItemViewModel.class);
            itemViewModel.setType(i.getClass().getSimpleName());
            return itemViewModel;
        }).collect(Collectors.toList()));

        return itemPage;
    }
}
