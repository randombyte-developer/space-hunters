package de.fragstyle.spacehunters.common.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
  private Body body;
  private final World world;

  private EntityState state;

  protected Entity(EntityState state, World world) {
    this.state = state;
    this.world = world;

    body = loadBody(world, state);
  }

  protected abstract Body loadBody(World world, EntityState entityState);

  public EntityState getState() {
    state.setPosition(body.getPosition());
    state.setRotation(body.getAngle() * MathUtils.radiansToDegrees);

    return state;
  }

  public Body getBody() {
    return body;
  }

  protected void setBody(Body body) {
    this.body = body;
  }

  public Vector2 getOrigin() {
    return body.getLocalCenter();
  }
  public World getWorld() {
    return world;
  }
}
