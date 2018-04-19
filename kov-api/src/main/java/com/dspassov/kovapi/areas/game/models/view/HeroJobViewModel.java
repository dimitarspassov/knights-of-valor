package com.dspassov.kovapi.areas.game.models.view;

public class HeroJobViewModel {

    private String id;
    private JobViewModel job;
    private Long secondsRemaining;

    public HeroJobViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JobViewModel getJob() {
        return job;
    }

    public void setJob(JobViewModel job) {
        this.job = job;
    }

    public Long getSecondsRemaining() {
        return secondsRemaining;
    }

    public void setSecondsRemaining(Long secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }
}
