package com.dspassov.kovapi.areas.game.common;


import com.dspassov.kovapi.areas.game.entities.HeroJob;

import java.time.LocalDateTime;

public final class JobFactory {

    public static HeroJob createNewHeroJob() {
        HeroJob newJob = new HeroJob();
        newJob.setStartTime(LocalDateTime.now());
        return newJob;
    }

}
