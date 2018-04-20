package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.models.binding.NeutralUnitBindingModel;

import com.dspassov.kovapi.areas.game.models.view.NeutralUnitPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.NeutralUnitViewModel;
import org.springframework.web.multipart.MultipartFile;


public interface NeutralUnitService {

    NeutralUnitPageViewModel findUnitsByPage(int page, int size);

    NeutralUnitPageViewModel findFreeUnitsByPage(int page, int size);

    String save(NeutralUnitBindingModel neutral, MultipartFile image);

    NeutralUnitViewModel getUnitById(String id);

    String updateUnit(String id, NeutralUnitBindingModel neutral, MultipartFile newImage);

    String changeStatus(String unitId, boolean newStatus);

    void triggerSpecialUnits(boolean makeFree);
}
