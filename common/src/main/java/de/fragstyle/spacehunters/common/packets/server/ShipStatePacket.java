package de.fragstyle.spacehunters.common.packets.server;

import java.util.UUID;

public class ShipStatePacket {

  private UUID uuid;
  private float x;
  private float y;
  private float rotation;
  private float rotationSpeed;
  private float xSpeed;
  private float ySpeed;
  private float acceleration;

  private ShipStatePacket() { }

  public ShipStatePacket(UUID uuid, float x, float y, float rotation, float rotationSpeed,
      float xSpeed, float ySpeed, float acceleration) {
    this.uuid = uuid;
    this.x = x;
    this.y = y;
    this.rotation = rotation;
    this.rotationSpeed = rotationSpeed;
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
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

  public float getRotationSpeed() {
    return rotationSpeed;
  }

  public void setRotationSpeed(float rotationSpeed) {
    this.rotationSpeed = rotationSpeed;
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

  public float getAcceleration() {
    return acceleration;
  }

  public void setAcceleration(float acceleration) {
    this.acceleration = acceleration;
  }
}
