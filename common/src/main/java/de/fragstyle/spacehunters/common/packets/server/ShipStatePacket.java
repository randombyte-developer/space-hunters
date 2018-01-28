package de.fragstyle.spacehunters.common.packets.server;

import java.util.UUID;

public class ShipStatePacket {

  private UUID uuid;
  private float x;
  private float y;
  private float rotation;
  private float xSpeed;
  private float ySpeed;
  private float xAcceleration;
  private float yAcceleration;

  private ShipStatePacket() { }

  public ShipStatePacket(UUID uuid, float x, float y, float rotation, float xSpeed, float ySpeed,
      float xAcceleration, float yAcceleration) {
    this.uuid = uuid;
    this.x = x;
    this.y = y;
    this.rotation = rotation;
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
    this.xAcceleration = xAcceleration;
    this.yAcceleration = yAcceleration;
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

  public float getXSpeed() {
    return xSpeed;
  }

  public void setXSpeed(float xSpeed) {
    this.xSpeed = xSpeed;
  }

  public float getYSpeed() {
    return ySpeed;
  }

  public void setYSpeed(float ySpeed) {
    this.ySpeed = ySpeed;
  }

  public float getXAcceleration() {
    return xAcceleration;
  }

  public void setXAcceleration(float xAcceleration) {
    this.xAcceleration = xAcceleration;
  }

  public float getYAcceleration() {
    return yAcceleration;
  }

  public void setYAcceleration(float yAcceleration) {
    this.yAcceleration = yAcceleration;
  }
}
