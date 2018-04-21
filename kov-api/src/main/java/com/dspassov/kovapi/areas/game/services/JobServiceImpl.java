package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.common.Toolbox;
import com.dspassov.kovapi.areas.game.entities.Job;
import com.dspassov.kovapi.areas.game.models.binding.JobBindingModel;
import com.dspassov.kovapi.areas.game.models.view.JobPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.JobViewModel;
import com.dspassov.kovapi.areas.cloud.CloudService;
import com.dspassov.kovapi.exceptions.CloudStorageException;
import com.dspassov.kovapi.exceptions.IllegalParamException;
import com.dspassov.kovapi.repositories.JobRepository;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;
    private final CloudService cloudService;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, ModelMapper modelMapper, CloudService cloudService) {
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
        this.cloudService = cloudService;
    }

    @Override
    public String save(JobBindingModel jobModel, MultipartFile image) {
        String fileName = image.getOriginalFilename();
        if (!Toolbox.isValidImage(fileName)) {
            throw new IllegalParamException(ResponseMessageConstants.INCORRECT_FILE_EXTENSION);
        }

        if (this.jobRepository.findByName(jobModel.getName()) != null) {
            throw new IllegalParamException(ResponseMessageConstants.EXISTING_JOB);
        }

        Job job = this.modelMapper.map(jobModel, Job.class);


        String imageUrl;
        try {
            imageUrl = this.cloudService.saveImage(image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CloudStorageException();
        }

        job.setImage(imageUrl);
        job.setStatus(true);

        this.jobRepository.save(job);

        return ResponseMessageConstants.JOB_ADDED;
    }

    @Override
    public JobViewModel getJobById(String id) {
        Job job = this.jobRepository.findById(id).orElse(null);

        if (job == null) {
            throw new IllegalParamException(ResponseMessageConstants.NON_EXISTENT_JOB);
        }
        JobViewModel model = this.modelMapper.map(job, JobViewModel.class);
        return model;
    }

    @Override
    public JobPageViewModel findJobsByPage(int page, int size) {

        Page<Job> jobs = this.jobRepository.findAllByStatus(true, PageRequest.of(page, size));
        return this.mapJobsToPageViewModel(jobs);
    }

    @Override
    public JobPageViewModel findJobsByPageRegardlessOfStatus(int page, int size) {
        Page<Job> jobs = this.jobRepository.findAll(PageRequest.of(page, size));
        return this.mapJobsToPageViewModel(jobs);
    }

    @Override
    public String updateJob(String id, JobBindingModel job, MultipartFile newImage) {

        Job current = this.jobRepository.findById(id).orElse(null);

        if (current == null) {
            throw new IllegalParamException(ResponseMessageConstants.NON_EXISTENT_JOB);
        }

        if (!job.getName().equals(current.getName()) && this.jobRepository.findByName(job.getName()) != null) {
            throw new IllegalParamException(ResponseMessageConstants.EXISTING_JOB);
        }

        current.setName(job.getName());
        current.setMinutes(job.getMinutes());
        current.setSalary(job.getSalary());

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

        this.jobRepository.save(current);
        return ResponseMessageConstants.JOB_EDITED;
    }

    @Override
    public String changeStatus(String jobId, boolean newStatus) {
        Job job = this.jobRepository.findById(jobId).orElse(null);

        if (job != null) {
            job.setStatus(newStatus);
            this.jobRepository.save(job);
        }

        return ResponseMessageConstants.JOB_EDITED;
    }

    private JobPageViewModel mapJobsToPageViewModel(Page<Job> jobs) {

        JobPageViewModel jobPage = this.modelMapper.map(jobs, JobPageViewModel.class);
        jobPage.setAllPages(jobs.getTotalPages());
        jobPage.setJobs(jobs.getContent()
                .stream()
                .map(j -> this.modelMapper.map(j, JobViewModel.class))
                .collect(Collectors.toList()));

        return jobPage;
    }
}
