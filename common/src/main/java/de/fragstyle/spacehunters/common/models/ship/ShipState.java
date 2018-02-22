package de.fragstyle.spacehunters.common.models.ship;

import de.fragstyle.spacehunters.common.models.EntityState;
import java.util.UUID;

public class ShipState extends EntityState {

  protected ShipState() { }

  public ShipState(UUID uuid, float x, float y, float rotation, float originX, float originY) {
    super(uuid, x, y, rotation, originX, originY);
  }

  public ShipState(EntityState entityState) {
    super(entityState);
  }
}