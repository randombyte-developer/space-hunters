package de.fragstyle.spacehunters.common.models;

import java.util.UUID;

public class EntityState {
  private UUID uuid;

  private float x;
  private float y;
  private float rotation;

  protected EntityState() { }

  public EntityState(EntityState entityState) {
    this.uuid = entityState.uuid;
    this.x = entityState.x;
    this.y = entityState.y;
    this.rotation = entityState.rotation;
  }

  public EntityState(UUID uuid, float x, float y, float rotation) {
    this.uuid = uuid;
    this.x = x;
    this.y = y;
    this.rotation = rotation;
  }

  public UUID getUuid() {
    return uuid;
  }

  public float getX() {
    return x;
  }

  public void setX(float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getRotation() {
    return rotation;
  }

  public void setRotation(float rotation) {
    this.rotation = rotation;
  }
}
