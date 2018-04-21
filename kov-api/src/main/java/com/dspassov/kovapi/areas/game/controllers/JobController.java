package com.dspassov.kovapi.areas.game.controllers;

import com.dspassov.kovapi.areas.game.models.binding.JobBindingModel;
import com.dspassov.kovapi.areas.game.models.view.JobPageViewModel;
import com.dspassov.kovapi.areas.game.models.view.JobViewModel;
import com.dspassov.kovapi.areas.game.services.JobService;
import com.dspassov.kovapi.web.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
public class JobController extends BaseController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/api/admin/jobs/add")
    @ResponseBody
    public String addJob(
            @RequestPart("job") @Valid JobBindingModel job,
            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        return this.success(this.jobService.save(job, image));
    }

    @GetMapping("/api/jobs")
    @ResponseBody
    public String allJobs(@RequestParam("page") int page, @RequestParam("size") int size) {
        JobPageViewModel model = this.jobService.findJobsByPage(page, size);
        return this.objectToJson(model);
    }

    @GetMapping("/api/admin/jobs")
    @ResponseBody
    public String allJobsForAdmin(@RequestParam("page") int page, @RequestParam("size") int size) {
        JobPageViewModel model = this.jobService.findJobsByPageRegardlessOfStatus(page, size);
        return this.objectToJson(model);
    }

    @GetMapping("/api/admin/jobs/{id}")
    @ResponseBody
    public String editJob(@PathVariable String id) {

        JobViewModel job = this.jobService.getJobById(id);
        return this.objectToJson(job);
    }

    @PostMapping("/api/admin/jobs/{id}")
    @ResponseBody
    public String editJob(
            @PathVariable String id,
            @RequestBody @Valid JobBindingModel job,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }


        return this.success(this.jobService.updateJob(id, job, null));
    }

    @PostMapping("/api/admin/jobs/newimg/{id}")
    @ResponseBody
    public String editJobWithNewImage(
            @PathVariable String id,
            @RequestPart("job") @Valid JobBindingModel job,
            @RequestPart("image") @Valid @NotNull @NotBlank MultipartFile image,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        return this.success(this.jobService.updateJob(id, job, image));
    }

    @PostMapping("/api/admin/jobs/{id}/status/{status}")
    public String changeStatus(@PathVariable String id, @PathVariable boolean status) {

        return this.success(this.jobService.changeStatus(id, status));
    }
}
