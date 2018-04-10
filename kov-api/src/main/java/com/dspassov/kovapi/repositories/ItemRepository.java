package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<Item, String> {

    Item findByName(String name);

    @Override
    List<Item> findAll();

    Page<Item> findAll(Pageable pageable);
}
