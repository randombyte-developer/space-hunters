package de.fragstyle.spacehunters.common.packets.client;

import de.fragstyle.spacehunters.common.models.Player;

public class LoginRequestPacket {

  private Player player;

  private LoginRequestPacket() { }

  public LoginRequestPacket(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
