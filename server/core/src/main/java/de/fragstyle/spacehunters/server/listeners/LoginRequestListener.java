package de.fragstyle.spacehunters.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.common.Player;
import de.fragstyle.spacehunters.common.packets.login.Disconnected;
import de.fragstyle.spacehunters.common.packets.login.LoginAccepted;
import de.fragstyle.spacehunters.common.packets.login.LoginRequest;
import de.fragstyle.spacehunters.server.PlayerList;
import de.fragstyle.spacehunters.server.ServerPlayer;

public class LoginRequestListener extends Listener {

  private final PlayerList playerList;

  public LoginRequestListener(PlayerList playerList) {
    this.playerList = playerList;
  }

  @Override
  public void disconnected(Connection connection) {
    super.disconnected(connection);

    playerList.removeByKryoNetClientId(connection.getID());
  }

  @Override
  public void received(Connection connection, Object object) {
    super.received(connection, object);

    if (object instanceof LoginRequest) {
      LoginRequest loginRequest = (LoginRequest) object;
      Player player = loginRequest.getPlayer();

      if (playerList.isNameAlreadyInUse(player.getName())) {
        connection.sendTCP(Disconnected.PLAYER_NAME_ALREADY_IN_USE);
        connection.close();
        return;
      }

      boolean success = playerList.add(ServerPlayer.fromPlayer(player, connection));
      // check if UUID of Player already in use
      if (!success) {
        connection.sendTCP(Disconnected.PLAYER_ID_ALREADY_IN_USE);
        connection.close();
        return;
      }

      connection.sendTCP(new LoginAccepted());
    }
  }
}
