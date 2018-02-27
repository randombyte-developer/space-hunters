package de.fragstyle.spacehunters.common.models.wall;

import com.badlogic.gdx.math.Vector2;
import de.fragstyle.spacehunters.common.models.EntityState;
import de.fragstyle.spacehunters.common.models.EntityType;

public class WallState extends EntityState {

  private Vector2 dimensions;

  protected WallState() { }

  public WallState(EntityState entityState, Vector2 dimensions) {
    super(entityState, EntityType.WALL);

    this.dimensions = dimensions;
  }

  public Vector2 getDimensions() {
    return dimensions;
  }
}