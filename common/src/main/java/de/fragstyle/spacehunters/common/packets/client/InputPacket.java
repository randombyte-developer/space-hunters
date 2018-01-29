package de.fragstyle.spacehunters.common.packets.client;

public class InputPacket {

  private short acceleration;
  private short rotation;

  private InputPacket() { }

  public InputPacket(short acceleration, short rotation) {
    this.acceleration = acceleration;
    this.rotation = rotation;
  }

  public short getAcceleration() {
    return acceleration;
  }

  public short getRotation() {
    return rotation;
  }
}
