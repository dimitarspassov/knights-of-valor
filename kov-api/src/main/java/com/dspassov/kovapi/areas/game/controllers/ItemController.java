package com.dspassov.kovapi.areas.game.controllers;

import com.dspassov.kovapi.areas.game.models.binding.ItemBindingModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import com.dspassov.kovapi.areas.game.services.ItemService;
import com.dspassov.kovapi.web.BaseController;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
public class ItemController extends BaseController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/api/admin/items/add")
    @ResponseBody
    public String addItem(
            @RequestPart("item") @Valid ItemBindingModel item,
            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        try {
            return this.success(this.itemService.save(item, image));
        } catch (IllegalArgumentException exception) {
            return this.error(exception.getMessage());
        } catch (IOException ioException) {
            return this.error(ResponseMessageConstants.FILE_UPLOAD_FAILED);
        }
    }

    @GetMapping("/api/items")
    @ResponseBody
    public String allItems() {
        List<ItemViewModel> items = this.itemService.findAllItems();
        return this.objectToJson(items);
    }

    @GetMapping("/api/admin/items/{id}")
    @ResponseBody
    public String editItem(@PathVariable String id) {

        try {
            ItemViewModel item = this.itemService.getItemById(id);
            return this.objectToJson(item);
        } catch (IllegalArgumentException e) {
            return this.error(e.getMessage());
        }
    }

    @PostMapping("/api/admin/items/{id}")
    @ResponseBody
    public String editItem(
            @PathVariable String id,
            @RequestBody @Valid ItemBindingModel item,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        try {
            return this.success(this.itemService.updateItem(id, item, null));
        } catch (IllegalArgumentException exception) {
            return this.error(exception.getMessage());
        } catch (IOException e) {
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/api/admin/items/newimg/{id}")
    @ResponseBody
    public String editItemWithNewImage(
            @PathVariable String id,
            @RequestPart("item") @Valid ItemBindingModel item,
            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        //todo: refactor
        try {
            return this.success(this.itemService.updateItem(id, item, image));
        } catch (IllegalArgumentException exception) {
            return this.error(exception.getMessage());
        } catch (IOException e) {
            return this.error(ResponseMessageConstants.FILE_UPLOAD_FAILED);
        }
    }

}
