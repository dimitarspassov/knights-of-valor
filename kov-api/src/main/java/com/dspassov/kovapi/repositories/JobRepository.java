package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.Job;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, String> {

    Job findByName(String name);

    @Override
    List<Job> findAll();

    Page<Job> findAll(Pageable pageable);

    Page<Job> findAllByStatus(boolean status, Pageable pageable);
}
