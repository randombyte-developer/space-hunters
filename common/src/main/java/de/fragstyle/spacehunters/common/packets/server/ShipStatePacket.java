package de.fragstyle.spacehunters.common.packets.server;

import java.util.UUID;

public class ShipStatePacket {

  private UUID uuid;
  private float x;
  private float y;
  private float rotation;
  private float speed;
  private float acceleration;

  private ShipStatePacket() { }

  public ShipStatePacket(UUID uuid, float x, float y, float rotation, float speed, float acceleration) {
    this.uuid = uuid;
    this.x = x;
    this.y = y;
    this.rotation = rotation;
    this.speed = speed;
    this.acceleration = acceleration;
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

  public float getSpeed() {
    return speed;
  }

  public void setSpeed(float speed) {
    this.speed = speed;
  }

  public float getAcceleration() {
    return acceleration;
  }

  public void setAcceleration(float acceleration) {
    this.acceleration = acceleration;
  }
}
