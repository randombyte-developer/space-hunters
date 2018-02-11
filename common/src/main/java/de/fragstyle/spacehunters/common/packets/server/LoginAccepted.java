package de.fragstyle.spacehunters.common.packets.server;

public class LoginAccepted {

  private Player player;

  private LoginAccepted() { }

  public LoginAccepted(Player player) {
    this.player = player;
  }

  public Player getPlayer() {
    return player;
  }
}
