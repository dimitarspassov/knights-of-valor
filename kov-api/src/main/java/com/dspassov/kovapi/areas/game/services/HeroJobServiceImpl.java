package com.dspassov.kovapi.areas.game.services;


import com.dspassov.kovapi.areas.game.common.JobFactory;
import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.entities.HeroJob;
import com.dspassov.kovapi.areas.game.entities.Job;
import com.dspassov.kovapi.areas.game.models.view.HeroJobViewModel;
import com.dspassov.kovapi.areas.game.models.view.JobViewModel;
import com.dspassov.kovapi.exceptions.HeroWorkException;
import com.dspassov.kovapi.repositories.HeroJobRepository;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class HeroJobServiceImpl implements HeroJobService {

    private final HeroJobRepository heroJobRepository;
    private final HeroService heroService;
    private final JobService jobService;
    private final ModelMapper modelMapper;

    @Autowired
    public HeroJobServiceImpl(HeroJobRepository heroJobRepository, HeroService heroService, JobService jobService, ModelMapper modelMapper) {
        this.heroJobRepository = heroJobRepository;
        this.heroService = heroService;
        this.jobService = jobService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String startJob(String jobId) {
        if (this.isAtWork()) {
            throw new HeroWorkException();
        }
        Hero hero = this.modelMapper.map(this.heroService.getCurrentHeroServiceModel(), Hero.class);

        HeroJob newJob = JobFactory.createNewHeroJob();
        JobViewModel job = this.jobService.getJobById(jobId);
        newJob.setEndTime(newJob.getStartTime().plusMinutes(job.getMinutes()));

        newJob.setJob(this.modelMapper.map(job, Job.class));
        newJob.setHero(hero);

        this.heroJobRepository.save(newJob);

        return String.format(ResponseMessageConstants.JOB_STARTED, job.getName());
    }

    @Override
    public Boolean isAtWork() {
        Hero hero = this.modelMapper.map(this.heroService.getCurrentHeroServiceModel(), Hero.class);

        return this.heroJobRepository.findByHero(hero) != null;
    }

    @Override
    public HeroJobViewModel getCurrentJob() {

        if (this.isAtWork()) {
            Hero hero = this.modelMapper.map(this.heroService.getCurrentHeroServiceModel(), Hero.class);
            HeroJob job = this.heroJobRepository.findByHero(hero);
            HeroJobViewModel model = this.modelMapper.map(job, HeroJobViewModel.class);

            model.setJob(this.modelMapper.map(job.getJob(), JobViewModel.class));
            Duration diff = Duration.between(LocalDateTime.now(), job.getEndTime());
            model.setSecondsRemaining(diff.toSeconds());

            return model;
        }

        return new HeroJobViewModel();
    }

    @Override
    public String abandonJob(String jobId) {
        if (!this.isAtWork()) {
            throw new HeroWorkException(ResponseMessageConstants.HERO_NOT_AT_WORK);
        }

        HeroJob job = this.heroJobRepository.findById(jobId).orElse(null);

        if (job == null) {
            throw new HeroWorkException(ResponseMessageConstants.NON_EXISTENT_JOB);
        }

        Duration diff = Duration.between(LocalDateTime.now(), job.getEndTime());

        if (diff.getSeconds() <= 0) {
            throw new HeroWorkException(ResponseMessageConstants.JOB_ALREADY_DONE);
        }

        Integer jobMinutes = job.getJob().getMinutes();
        Long minutesDiff = jobMinutes - diff.toMinutes();
        Long salary = (minutesDiff / 60) * job.getJob().getSalary();

        this.heroService.payHeroSalary(salary);

        this.heroJobRepository.deleteById(jobId);

        return String.format(ResponseMessageConstants.JOB_ABANDONED, salary);
    }

    @Override
    public String finishJob(String jobId) {
        if (!this.isAtWork()) {
            throw new HeroWorkException(ResponseMessageConstants.HERO_NOT_AT_WORK);
        }

        HeroJob job = this.heroJobRepository.findById(jobId).orElse(null);

        if (job == null) {
            throw new HeroWorkException(ResponseMessageConstants.NON_EXISTENT_JOB);
        }

        Duration diff = Duration.between(LocalDateTime.now(), job.getEndTime());

        if (diff.getSeconds() > 0) {
            throw new HeroWorkException(ResponseMessageConstants.HERO_AT_WORK);
        }

        Long salary = (long) (job.getJob().getSalary() * job.getJob().getMinutes());

        this.heroService.payHeroSalary(salary);
        this.heroJobRepository.deleteById(jobId);
        return String.format(ResponseMessageConstants.JOB_FINISHED, salary);
    }
}
