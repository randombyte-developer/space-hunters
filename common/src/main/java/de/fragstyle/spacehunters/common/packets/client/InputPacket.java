package de.fragstyle.spacehunters.common.packets.client;

public class InputPacket {

  private short x;
  private short y;

  private InputPacket() { }

  public InputPacket(short x, short y) {
    this.x = x;
    this.y = y;
  }

  public short getX() {
    return x;
  }

  public short getY() {
    return y;
  }
}
