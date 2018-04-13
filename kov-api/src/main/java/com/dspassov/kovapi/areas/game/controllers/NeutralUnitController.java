package com.dspassov.kovapi.areas.game.controllers;

import com.dspassov.kovapi.areas.game.models.binding.NeutralUnitBindingModel;
import com.dspassov.kovapi.areas.game.models.view.NeutralUnitPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.NeutralUnitViewModel;
import com.dspassov.kovapi.areas.game.services.NeutralUnitService;
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
public class NeutralUnitController extends BaseController {

    private final NeutralUnitService unitService;

    @Autowired
    public NeutralUnitController(NeutralUnitService unitService) {
        this.unitService = unitService;
    }


    @PostMapping("/api/admin/neutrals/add")
    @ResponseBody
    public String addNeutralUnit(
            @RequestPart("unit") @Valid NeutralUnitBindingModel unit,
            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        try {
            return this.success(this.unitService.save(unit, image));
        } catch (IllegalArgumentException | CloudStorageException exception) {
            return this.error(exception.getMessage());
        } catch (Exception e) {
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @GetMapping("/api/neutrals")
    @ResponseBody
    public String allNeutralsByPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        NeutralUnitPageViewModel model = this.unitService.findUnitsByPage(page, size);
        return this.objectToJson(model);
    }

    @GetMapping("/api/admin/neutrals/{id}")
    @ResponseBody
    public String editNeutralUnit(@PathVariable String id) {

        try {
            NeutralUnitViewModel unit = this.unitService.getUnitById(id);
            return this.objectToJson(unit);
        } catch (IllegalArgumentException e) {
            return this.error(e.getMessage());
        }
    }

    @PostMapping("/api/admin/neutrals/{id}")
    @ResponseBody
    public String editNeutralUnit(
            @PathVariable String id,
            @RequestBody @Valid NeutralUnitBindingModel unit,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        try {
            return this.success(this.unitService.updateUnit(id, unit, null));
        } catch (IllegalArgumentException | CloudStorageException exception) {
            return this.error(exception.getMessage());
        } catch (Exception e) {
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/api/admin/neutrals/newimg/{id}")
    @ResponseBody
    public String editNeutralUnitWithNewImage(
            @PathVariable String id,
            @RequestPart("unit") @Valid NeutralUnitBindingModel unit,
            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        try {
            return this.success(this.unitService.updateUnit(id, unit, image));
        } catch (IllegalArgumentException | CloudStorageException exception) {
            return this.error(exception.getMessage());
        } catch (Exception e) {
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/api/admin/neutrals/{id}/status/{status}")
    public String changeStatus(@PathVariable String id, @PathVariable boolean status) {

        try {
            return this.success(this.unitService.changeStatus(id, status));
        } catch (Exception e) {
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }

    }
}
