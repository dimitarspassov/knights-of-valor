package com.dspassov.kovapi.areas.game.models.view;

import java.util.ArrayList;
import java.util.List;

public class JobPageViewModel {

    private Integer size;
    private List<JobViewModel> jobs;
    private Integer allPages;

    public JobPageViewModel() {
        this.setJobs(new ArrayList<>());
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<JobViewModel> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobViewModel> jobs) {
        this.jobs = jobs;
    }

    public Integer getAllPages() {
        return allPages;
    }

    public void setAllPages(Integer allPages) {
        this.allPages = allPages;
    }
}
