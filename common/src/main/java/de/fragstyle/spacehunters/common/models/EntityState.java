package de.fragstyle.spacehunters.common.models;

import java.util.UUID;

public class EntityState {
  private UUID uuid;

  private float x;
  private float y;
  private float rotation;
  private float originX;
  private float originY;

  protected EntityState() { }

  public EntityState(EntityState entityState) {
    this.uuid = entityState.uuid;
    this.x = entityState.x;
    this.y = entityState.y;
    this.rotation = entityState.rotation;
    this.originX = entityState.originX;
    this.originY = entityState.originY;
  }

  public EntityState(UUID uuid, float x, float y, float rotation, float originX, float originY) {
    this.uuid = uuid;
    this.x = x;
    this.y = y;
    this.rotation = rotation;
    this.originX = originX;
    this.originY = originY;
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

  public float getOriginX() {
    return originX;
  }

  public void setOriginX(float originX) {
    this.originX = originX;
  }

  public float getOriginY() {
    return originY;
  }

  public void setOriginY(float originY) {
    this.originY = originY;
  }
}
