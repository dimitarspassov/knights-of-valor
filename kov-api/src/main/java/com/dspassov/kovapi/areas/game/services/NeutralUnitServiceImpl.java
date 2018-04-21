package com.dspassov.kovapi.areas.game.services;


import com.dspassov.kovapi.areas.game.common.Toolbox;
import com.dspassov.kovapi.areas.game.entities.NeutralUnit;
import com.dspassov.kovapi.areas.game.enumerations.NeutralUnitType;
import com.dspassov.kovapi.areas.game.models.binding.NeutralUnitBindingModel;
import com.dspassov.kovapi.areas.game.models.view.NeutralUnitPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.NeutralUnitViewModel;
import com.dspassov.kovapi.areas.cloud.CloudService;

import com.dspassov.kovapi.exceptions.CloudStorageException;
import com.dspassov.kovapi.exceptions.IllegalParamException;
import com.dspassov.kovapi.repositories.NeutralUnitRepository;
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
public class NeutralUnitServiceImpl implements NeutralUnitService {

    private final NeutralUnitRepository unitRepository;
    private final ModelMapper modelMapper;
    private final CloudService cloudService;

    @Autowired
    public NeutralUnitServiceImpl(NeutralUnitRepository unitRepository, ModelMapper modelMapper, CloudService cloudService) {
        this.unitRepository = unitRepository;
        this.modelMapper = modelMapper;
        this.cloudService = cloudService;
    }

    @Override
    public String save(NeutralUnitBindingModel unitModel, MultipartFile image) {
        String fileName = image.getOriginalFilename();
        if (!Toolbox.isValidImage(fileName)) {
            throw new IllegalParamException(ResponseMessageConstants.INCORRECT_FILE_EXTENSION);
        }

        if (this.unitRepository.findByName(unitModel.getName()) != null) {
            throw new IllegalParamException(ResponseMessageConstants.EXISTING_UNIT);
        }

        NeutralUnit unit = this.modelMapper.map(unitModel, NeutralUnit.class);


        String imageUrl;
        try {
            imageUrl = this.cloudService.saveImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CloudStorageException();
        }

        unit.setType(unitModel.getType().equals(NeutralUnitType.Special.name())
                ? NeutralUnitType.Special : NeutralUnitType.Regular);
        unit.setImage(imageUrl);
        unit.setStatus(true);
        unit.setFree(unit.getType().equals(NeutralUnitType.Regular));
        this.unitRepository.save(unit);

        return ResponseMessageConstants.UNIT_ADDED;
    }


    @Override
    public NeutralUnitViewModel getUnitById(String id) {
        NeutralUnit unit = this.unitRepository.findById(id).orElse(null);

        if (unit == null) {
            throw new IllegalParamException(ResponseMessageConstants.NON_EXISTENT_UNIT);
        }

        NeutralUnitViewModel model = this.modelMapper.map(unit, NeutralUnitViewModel.class);
        model.setType(unit.getType().name());
        return model;
    }

    @Override
    public NeutralUnitPageViewModel findUnitsByPageRegardlessOfStatus(int page, int size) {

        Page<NeutralUnit> units = this.unitRepository.findAll(PageRequest.of(page, size));
        return this.mapUnitsToPage(units);
    }

    @Override
    public NeutralUnitPageViewModel findFreeUnitsByPage(int page, int size) {
        Page<NeutralUnit> units = this.unitRepository
                .findAllByFreeAndStatus(
                        true,
                        true,
                        PageRequest.of(page, size));
        return this.mapUnitsToPage(units);
    }

    @Override
    public String updateUnit(String id, NeutralUnitBindingModel unit, MultipartFile newImage) {

        NeutralUnit current = this.unitRepository.findById(id).orElse(null);

        if (current == null) {
            throw new IllegalParamException(ResponseMessageConstants.NON_EXISTENT_UNIT);
        }

        if (!unit.getName().equals(current.getName()) && this.unitRepository.findByName(unit.getName()) != null) {
            throw new IllegalParamException(ResponseMessageConstants.EXISTING_UNIT);
        }

        current.setName(unit.getName());
        current.setHealth(unit.getHealth());
        current.setLevel(unit.getLevel());
        current.setStrength(unit.getStrength());
        current.setStamina(unit.getStamina());
        current.setDefense(unit.getDefense());
        current.setLootGold(unit.getLootGold());

        if (newImage != null) {

            if (!Toolbox.isValidImage(newImage.getOriginalFilename())) {
                throw new IllegalParamException(ResponseMessageConstants.INCORRECT_FILE_EXTENSION);
            }

            try {
                this.cloudService.deleteImage(Toolbox.getImageId(current.getImage()));
                current.setImage(this.cloudService.saveImage(newImage));
            } catch (IOException e) {
                throw new CloudStorageException();
            }

        }

        this.unitRepository.save(current);
        return ResponseMessageConstants.UNIT_EDITED;
    }

    @Override
    public String changeStatus(String unitId, boolean newStatus) {
        NeutralUnit unit = this.unitRepository.findById(unitId).orElse(null);

        if (unit != null) {
            unit.setStatus(newStatus);
            this.unitRepository.save(unit);
        }

        return ResponseMessageConstants.UNIT_EDITED;
    }

    @Override
    public void triggerSpecialUnits(boolean makeFree) {
        List<NeutralUnit> units = this.unitRepository.findAllByType(NeutralUnitType.Special);
        for (NeutralUnit unit : units) {
            unit.setFree(makeFree);
            this.unitRepository.save(unit);
        }
    }

    private NeutralUnitPageViewModel mapUnitsToPage(Page<NeutralUnit> units) {
        NeutralUnitPageViewModel unitPage = this.modelMapper.map(units, NeutralUnitPageViewModel.class);
        unitPage.setAllPages(units.getTotalPages());
        unitPage.setUnits(units.getContent()
                .stream()
                .map(u -> this.modelMapper.map(u, NeutralUnitViewModel.class))
                .collect(Collectors.toList()));

        return unitPage;
    }
}
