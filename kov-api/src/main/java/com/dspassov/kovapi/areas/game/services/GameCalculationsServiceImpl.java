package com.dspassov.kovapi.areas.game.services;

import org.springframework.stereotype.Service;

@Service
public class GameCalculationsServiceImpl implements GameCalculationsService {

    @Override
    public Integer getInventoryItemPrice(Integer originalItemPrice) {
        return originalItemPrice / 3;
    }
}
