package de.fragstyle.spacehunters.common.packets.server;

import de.fragstyle.spacehunters.common.models.Player;

public class LoginAcceptedPacket {

  private Player player;

  private LoginAcceptedPacket() { }

  public LoginAcceptedPacket(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
