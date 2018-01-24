package de.fragstyle.spacehunters.common.packets.server;

import java.util.List;

public class Players {

  private List<ShipPacket> players;

  private Players() { }

  public Players(List<ShipPacket> players) {
    this.players = players;
  }

  public List<ShipPacket> getPlayers() {
    return players;
  }
}
