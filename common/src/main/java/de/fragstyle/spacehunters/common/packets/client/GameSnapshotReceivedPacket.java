package de.fragstyle.spacehunters.common.packets.client;

public class GameSnapshotReceivedPacket {

  private long snapshotTime;

  private GameSnapshotReceivedPacket() { }

  public GameSnapshotReceivedPacket(long snapshotTime) {
    this.snapshotTime = snapshotTime;
  }

  public long getSnapshotTime() {
    return snapshotTime;
  }
}
