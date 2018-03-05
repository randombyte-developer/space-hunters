package de.fragstyle.spacehunters.common.models.entities.ship;

import de.fragstyle.spacehunters.common.models.entities.EntityState;
import de.fragstyle.spacehunters.common.models.entities.EntityType;

public class ShipState extends EntityState {

  protected ShipState() { }

  public ShipState(EntityState entityState) {
    super(entityState, EntityType.SHIP);
  }
}