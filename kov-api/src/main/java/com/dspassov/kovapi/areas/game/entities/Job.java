package com.dspassov.kovapi.areas.game.entities;

import com.dspassov.kovapi.areas.game.common.GameDomainConstants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Length(min = GameDomainConstants.JOB_NAME_MIN_LENGTH, max = GameDomainConstants.JOB_NAME_MAX_LENGTH)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Min(GameDomainConstants.JOB_MINUTES_MIN)
    @Max(GameDomainConstants.JOB_MINUTES_MAX)
    @Column(name = "minutes")
    private Integer minutes;

    @NotNull
    @Min(GameDomainConstants.JOB_SALARY_MIN)
    @Max(GameDomainConstants.JOB_SALARY_MAX)
    @Column(name = "salary")
    private Integer salary;


    @NotNull
    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "status")
    private Boolean status;

    public Job() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
