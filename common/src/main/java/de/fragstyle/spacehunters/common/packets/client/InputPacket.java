package de.fragstyle.spacehunters.common.packets.client;

import com.badlogic.gdx.math.MathUtils;

public class InputPacket {

  private static final short MIN = (short) -1;
  private static final short MAX = (short) +1;

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

  public InputPacket clamp() {
    return new InputPacket(MathUtils.clamp(acceleration, MIN, MAX), MathUtils.clamp(rotation, MIN, MAX));
  }
}
