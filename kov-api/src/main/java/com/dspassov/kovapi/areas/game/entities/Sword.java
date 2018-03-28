package com.dspassov.kovapi.areas.game.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "sword")
public class Sword extends Item {

    public Sword() {
    }
}
