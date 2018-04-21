package com.dspassov.kovapi.areas.game.controllers;

import com.dspassov.kovapi.areas.game.models.binding.ItemBindingModel;
import com.dspassov.kovapi.areas.game.models.view.ItemPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import com.dspassov.kovapi.areas.game.services.ItemService;
import com.dspassov.kovapi.web.BaseController;

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

    @GetMapping("/api/admin/items")
    @RequestMapping(method = RequestMethod.GET, params = {"page", "size"}, path = "/api/admin/items")
    @ResponseBody
    public String allItemsByPageForAdmin(@RequestParam("page") int page, @RequestParam("size") int size) {

        ItemPageViewModel model = this.itemService.findItemsByPageRegardlessOfStatus(page, size);
        return this.objectToJson(model);
    }

    @GetMapping("/api/items")
    @RequestMapping(method = RequestMethod.GET, params = {"page", "size"})
    @ResponseBody
    public String allItemsByPage(@RequestParam("page") int page, @RequestParam("size") int size) {

        ItemPageViewModel model = this.itemService.findItemsByPage(page, size);
        return this.objectToJson(model);
    }

    @GetMapping("/api/items")
    @RequestMapping(method = RequestMethod.GET, params = {"page", "size", "type"})
    @ResponseBody
    public String allItemsByPageAndType(@RequestParam("page") int page,
                                        @RequestParam("size") int size,
                                        @RequestParam("type") String type) {

        ItemPageViewModel model = this.itemService.findItemsByPageAndType(page, size, type);
        return this.objectToJson(model);
    }


    @GetMapping("/api/items")
    @RequestMapping(method = RequestMethod.GET, params = {"page", "size", "search"})
    @ResponseBody
    public String allItemsByPageAndSearchQuery(@RequestParam("page") int page,
                                               @RequestParam("size") int size,
                                               @RequestParam("search") String searchQuery) {

        ItemPageViewModel model = this.itemService.findItemsByPageAndName(page, size, searchQuery);
        return this.objectToJson(model);
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

        return this.success(this.itemService.save(item, image));
    }

    @GetMapping("/api/admin/items/{id}")
    @ResponseBody
    public String editItem(@PathVariable String id) {

        ItemViewModel item = this.itemService.getItemById(id);
        return this.objectToJson(item);
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

        return this.success(this.itemService.updateItem(id, item, null));
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

        return this.success(this.itemService.updateItem(id, item, image));
    }

    @PostMapping("/api/admin/items/{id}/status/{status}")
    public String changeStatus(@PathVariable String id, @PathVariable boolean status) {

        return this.success(this.itemService.changeStatus(id, status));

    }

}
