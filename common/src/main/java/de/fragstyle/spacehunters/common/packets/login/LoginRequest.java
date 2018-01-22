package de.fragstyle.spacehunters.common.packets.login;

import de.fragstyle.spacehunters.common.Player;

public class LoginRequest {
  private Player player;

  private LoginRequest() {}

  public LoginRequest(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
