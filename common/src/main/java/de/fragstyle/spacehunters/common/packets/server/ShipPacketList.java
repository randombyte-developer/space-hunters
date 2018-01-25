package de.fragstyle.spacehunters.common.packets.server;

import java.util.List;

public class ShipPacketList {

  private List<ShipPacket> players;

  private ShipPacketList() { }

  public ShipPacketList(List<ShipPacket> players) {
    this.players = players;
  }

  public List<ShipPacket> getPlayers() {
    return players;
  }
}
