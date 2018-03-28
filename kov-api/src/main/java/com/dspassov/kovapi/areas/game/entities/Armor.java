package com.dspassov.kovapi.areas.game.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "armor")
public class Armor extends Item {

    public Armor() {
    }
}
