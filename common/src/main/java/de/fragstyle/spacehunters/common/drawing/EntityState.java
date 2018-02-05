package de.fragstyle.spacehunters.common.drawing;

import java.util.UUID;

public class EntityState {
  private UUID uuid;

  private float x;
  private float y;
  private float rotation;

  private EntityState(UUID uuid) {
    this.uuid = uuid;
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
