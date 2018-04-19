package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.InventoryItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {

    @Transactional
    void deleteAllById(String id);

}
