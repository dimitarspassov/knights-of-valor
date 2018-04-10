package com.dspassov.kovapi.areas.game.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dspassov.kovapi.areas.game.entities.Armor;
import com.dspassov.kovapi.areas.game.entities.Item;
import com.dspassov.kovapi.areas.game.entities.Shield;
import com.dspassov.kovapi.areas.game.entities.Weapon;
import com.dspassov.kovapi.areas.game.models.binding.ItemBindingModel;
import com.dspassov.kovapi.areas.game.models.view.ItemPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
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
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, Cloudinary cloudinary, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.cloudinary = cloudinary;
        this.modelMapper = modelMapper;
    }

    @Override
    public String save(ItemBindingModel itemModel, MultipartFile image) throws IOException {

        String fileName = image.getOriginalFilename();
        if (!this.isValidImage(fileName)) {
            throw new IllegalArgumentException(ResponseMessageConstants.INCORRECT_FILE_EXTENSION);
        }

        if (this.itemRepository.findByName(itemModel.getName()) != null) {
            throw new IllegalArgumentException(ResponseMessageConstants.EXISTING_ITEM);
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
                throw new IllegalArgumentException(ResponseMessageConstants.UNSUPPORTED_ITEM_TYPE);
        }

        Map upload = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
        String imageUrl = (String) upload.get("secure_url");

        item.setImage(imageUrl);

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

        ItemPageViewModel itemPage = this.modelMapper.map(items, ItemPageViewModel.class);
        itemPage.setAllPages(items.getTotalPages());
        itemPage.setItems(items.getContent().stream().map(i -> {

            ItemViewModel itemViewModel = this.modelMapper.map(i, ItemViewModel.class);
            itemViewModel.setType(i.getClass().getSimpleName());
            return itemViewModel;
        }).collect(Collectors.toList()));

        return itemPage;
    }

    @Override
    public ItemViewModel getItemById(String id) {
        Item item = this.itemRepository.findById(id).orElse(null);

        if (item == null) {
            throw new IllegalArgumentException(ResponseMessageConstants.NON_EXISTENT_ITEM);
        }

        ItemViewModel model = this.modelMapper.map(item, ItemViewModel.class);
        model.setType(item.getClass().getSimpleName());
        return model;
    }


    @Override
    public String updateItem(String id, ItemBindingModel item, MultipartFile newImage) throws IOException {

        Item current = this.itemRepository.findById(id).orElse(null);

        if (current == null) {
            throw new IllegalArgumentException(ResponseMessageConstants.NON_EXISTENT_ITEM);
        }

        current.setBonus(item.getBonus());
        current.setName(item.getName());
        current.setPrice(item.getPrice());

        if (newImage != null) {

            String currentImageId = current.getImage().substring(
                    current.getImage().lastIndexOf("/") + 1
            );

            currentImageId = currentImageId.substring(0, currentImageId.lastIndexOf("."));

            // todo: refactor
            this.cloudinary.uploader().destroy(currentImageId, ObjectUtils.emptyMap());

            Map upload = cloudinary.uploader().upload(newImage.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) upload.get("secure_url");

            current.setImage(imageUrl);
        }

        this.itemRepository.save(current);
        return ResponseMessageConstants.ITEM_EDITED;

    }

    private boolean isValidImage(String fileName) {
        return fileName.endsWith(".png")
                && !fileName.endsWith(".jpg")
                && !fileName.endsWith(".jpeg");
    }

}
