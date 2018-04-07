package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.game.entities.Inventory;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository  extends PagingAndSortingRepository<Inventory, String> {
}
