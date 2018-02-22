package de.fragstyle.spacehunters.common.models;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import java.util.UUID;

public class Entity {
  private final UUID uuid;
  private Body body;
  private Vector2 origin;
  private final World world;

  protected Entity(UUID uuid, World world) {
    this.uuid = uuid;
    this.world = world;
  }

  public EntityState getState() {
    return new EntityState(uuid, body.getPosition().x, body.getPosition().y, body.getAngle() * MathUtils.radiansToDegrees,
            body.getLocalCenter().x, body.getLocalCenter().y);
  }

  public UUID getUuid() {
    return uuid;
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

  protected void setOrigin(Vector2 origin) {
    this.origin = origin;
  }

  public World getWorld() {
    return world;
  }
}
