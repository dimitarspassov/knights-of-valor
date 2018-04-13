package com.dspassov.kovapi.areas.game.controllers;

import com.dspassov.kovapi.areas.game.models.binding.ItemBindingModel;
import com.dspassov.kovapi.areas.game.models.view.ItemPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import com.dspassov.kovapi.areas.game.services.ItemService;
import com.dspassov.kovapi.exceptions.CloudStorageException;
import com.dspassov.kovapi.web.BaseController;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


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
        } catch (IllegalArgumentException | CloudStorageException exception) {
            return this.error(exception.getMessage());
        } catch (Exception e) {
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @GetMapping("/api/items")
    @ResponseBody
    public String allItemsByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        ItemPageViewModel model = this.itemService.findItemsByPage(page, size);
        return this.objectToJson(model);
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
        } catch (IllegalArgumentException | CloudStorageException exception) {
            return this.error(exception.getMessage());
        } catch (Exception e) {
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

        try {
            return this.success(this.itemService.updateItem(id, item, image));
        } catch (IllegalArgumentException | CloudStorageException exception) {
            return this.error(exception.getMessage());
        } catch (Exception e) {
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/api/admin/items/{id}/status/{status}")
    public String changeStatus(@PathVariable String id, @PathVariable boolean status) {

        try {
            return this.success(this.itemService.changeStatus(id, status));
        } catch (Exception e) {
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }

    }
}
