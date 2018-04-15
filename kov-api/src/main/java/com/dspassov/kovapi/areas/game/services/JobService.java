package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.models.binding.JobBindingModel;

import com.dspassov.kovapi.areas.game.models.view.JobPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.JobViewModel;
import org.springframework.web.multipart.MultipartFile;


public interface JobService {

    String save(JobBindingModel job, MultipartFile image);

    JobPageViewModel findJobsByPage(int page, int size);

    JobViewModel getJobById(String id);

    String updateJob(String id, JobBindingModel job, MultipartFile newImage);

    String changeStatus(String jobId, boolean newStatus);
}
