package com.dspassov.kovapi.areas.game.models.binding;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;

import javax.validation.constraints.*;

public class JobBindingModel {

    @NotEmpty(message = "Job name cannot be empty.")
    @Size(min = GameDomainConstants.JOB_NAME_MIN_LENGTH, max = GameDomainConstants.JOB_NAME_MAX_LENGTH,
            message = "Job name must contain between "
                    + GameDomainConstants.JOB_NAME_MIN_LENGTH
                    + " and " + GameDomainConstants.JOB_NAME_MAX_LENGTH
                    + " symbols.")
    private String name;

    @NotNull(message = "Minutes for job cannot be null.")
    @Min(value = GameDomainConstants.JOB_MINUTES_MIN, message = "Minutes for job must be greater than or equal to " + GameDomainConstants.JOB_MINUTES_MIN)
    @Max(value = GameDomainConstants.JOB_MINUTES_MAX, message = "Minutes for job must be less or equal to " + GameDomainConstants.JOB_MINUTES_MAX)
    private Integer minutes;

    @NotNull(message = "Job salary cannot be null.")
    @Min(value = GameDomainConstants.JOB_SALARY_MIN, message = "Job salary must be greater than or equal to " + GameDomainConstants.JOB_SALARY_MIN)
    @Max(value = GameDomainConstants.JOB_SALARY_MAX, message = "Job salary must be less or equal to " + GameDomainConstants.JOB_SALARY_MAX)
    private Integer salary;


    public JobBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
