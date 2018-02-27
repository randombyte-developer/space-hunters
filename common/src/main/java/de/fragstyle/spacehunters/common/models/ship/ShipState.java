package de.fragstyle.spacehunters.common.models.ship;

import de.fragstyle.spacehunters.common.models.EntityState;
import de.fragstyle.spacehunters.common.models.EntityType;

public class ShipState extends EntityState {

  protected ShipState() { }

  public ShipState(EntityState entityState) {
    super(entityState, EntityType.SHIP);
  }
}