package com.dspassov.kovapi.areas.game.tasks;

import com.dspassov.kovapi.areas.game.services.NeutralUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final NeutralUnitService neutralUnitService;

    @Autowired
    public ScheduledTasks(NeutralUnitService neutralUnitService) {
        this.neutralUnitService = neutralUnitService;
    }

    @Scheduled(cron = "0 0 22 * * *")
    public void setSpecialUnitsFree() {
        this.neutralUnitService.triggerSpecialUnits(true);
    }

    @Scheduled(cron = "0 45 6 * * *")
    public void hideSpecialUnits() {
        this.neutralUnitService.triggerSpecialUnits(false);
    }
}
