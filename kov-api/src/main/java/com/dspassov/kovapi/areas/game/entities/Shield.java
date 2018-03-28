package com.dspassov.kovapi.areas.game.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(value = "shield")
public class Shield extends Item {

    public Shield() {
    }
}
