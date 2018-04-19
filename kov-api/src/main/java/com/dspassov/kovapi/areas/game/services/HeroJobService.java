package com.dspassov.kovapi.areas.game.services;


import com.dspassov.kovapi.areas.game.models.view.HeroJobViewModel;

public interface HeroJobService {


    String startJob(String jobId);

    String abandonJob(String jobId);

    String finishJob(String jobId);

    Boolean isAtWork();

    HeroJobViewModel getCurrentJob();
}
