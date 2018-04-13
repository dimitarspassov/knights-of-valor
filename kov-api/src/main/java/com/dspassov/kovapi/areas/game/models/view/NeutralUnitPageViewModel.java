package com.dspassov.kovapi.areas.game.models.view;

import java.util.ArrayList;
import java.util.List;

public class NeutralUnitPageViewModel {

    private Integer size;
    private List<NeutralUnitViewModel> units;
    private Integer allPages;

    public NeutralUnitPageViewModel() {
        this.setUnits(new ArrayList<>());
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<NeutralUnitViewModel> getUnits() {
        return units;
    }

    public void setUnits(List<NeutralUnitViewModel> units) {
        this.units = units;
    }

    public Integer getAllPages() {
        return allPages;
    }

    public void setAllPages(Integer allPages) {
        this.allPages = allPages;
    }
}
