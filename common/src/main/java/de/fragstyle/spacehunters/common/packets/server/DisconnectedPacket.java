package de.fragstyle.spacehunters.common.packets.server;

public class DisconnectedPacket {

  private String reason;

  public static final DisconnectedPacket PLAYER_NAME_ALREADY_IN_USE = new DisconnectedPacket(
      "Dein Spielername wird bereits verwendet!");
  public static final DisconnectedPacket PLAYER_ID_ALREADY_IN_USE = new DisconnectedPacket(
      "Deine Spieler-ID wird bereits verwendet!");

  private DisconnectedPacket() { }

  public DisconnectedPacket(String reason) {
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}
