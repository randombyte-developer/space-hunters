package de.fragstyle.spacehunters.common.packets.server;

public class Disconnected {

  private String reason;

  public static final Disconnected PLAYER_NAME_ALREADY_IN_USE = new Disconnected(
      "Dein Spielername wird bereits verwendet!");
  public static final Disconnected PLAYER_ID_ALREADY_IN_USE = new Disconnected(
      "Deine Spieler-ID wird bereits verwendet!");

  private Disconnected() { }

  public Disconnected(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
