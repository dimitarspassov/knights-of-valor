package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.NeutralUnit;
import com.dspassov.kovapi.areas.game.enumerations.NeutralUnitType;
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

    List<NeutralUnit> findAllByType(NeutralUnitType type);

    Page<NeutralUnit> findAll(Pageable pageable);

    Page<NeutralUnit> findAllByFreeAndStatus(Boolean isFree, boolean status, Pageable pageable);
}
