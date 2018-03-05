package de.fragstyle.spacehunters.common.models.entities;

import com.badlogic.gdx.math.Vector2;
import java.util.UUID;

public class EntityState {
  private UUID uuid;

  private EntityType entityType;

  private Vector2 position;
  private float rotation;
  private Vector2 origin;

  protected EntityState() { }

  public EntityState(UUID uuid, Vector2 position, float rotation, Vector2 origin) {
    this.uuid = uuid;
    this.position = position;
    this.rotation = rotation;
    this.origin = origin;
  }

  public EntityState(EntityState entityState, EntityType entityType) {
    this.uuid = entityState.uuid;
    this.entityType = entityType;
    this.position = entityState.position;
    this.rotation = entityState.rotation;
    this.origin = entityState.origin;
  }

  public UUID getUuid() {
    return uuid;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public Vector2 getPosition() {
    return position;
  }

  public void setPosition(Vector2 position) {
    this.position = position;
  }

  public float getRotation() {
    return rotation;
  }

  public void setRotation(float rotation) {
    this.rotation = rotation;
  }

  public Vector2 getOrigin() {
    return origin;
  }

  public void setOrigin(Vector2 origin) {
    this.origin = origin;
  }
}
