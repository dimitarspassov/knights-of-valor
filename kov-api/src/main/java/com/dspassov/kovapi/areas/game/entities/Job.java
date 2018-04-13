package com.dspassov.kovapi.areas.game.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "jobs")
public class Job {

    private static final int NAME_MIN_LENGTH = 3;
    private static final int NAME_MAX_LENGTH = 40;
    private static final int MINUTES_MIN = 60;
    private static final int MINUTES_MAX = 600;
    private static final int SALARY_MIN = 100;
    private static final int SALARY_MAX = 1000000;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Length(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH)
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Min(MINUTES_MIN)
    @Max(MINUTES_MAX)
    @Column(name = "minutes")
    private Integer minutes;

    @NotNull
    @Min(SALARY_MIN)
    @Max(SALARY_MAX)
    @Column(name = "salary")
    private Integer salary;

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
}
