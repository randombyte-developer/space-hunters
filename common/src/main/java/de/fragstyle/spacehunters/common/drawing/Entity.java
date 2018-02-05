package de.fragstyle.spacehunters.common.drawing;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import java.util.UUID;

public class Entity {
  private final UUID uuid;
  private Body body;
  private final World world;

  protected Entity(UUID uuid, World world) {
    this.uuid = uuid;
    this.world = world;
  }

  public EntityState getState() {
    return new EntityState(uuid, body.getPosition().x, body.getPosition().y, body.getAngle() + MathUtils.radiansToDegrees);
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

  public World getWorld() {
    return world;
  }
}
