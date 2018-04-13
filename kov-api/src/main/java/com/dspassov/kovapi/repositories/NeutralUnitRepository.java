package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.NeutralUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeutralUnitRepository extends PagingAndSortingRepository<NeutralUnit, String> {

    NeutralUnit findByName(String name);

    @Override
    List<NeutralUnit> findAll();

    Page<NeutralUnit> findAll(Pageable pageable);
}
