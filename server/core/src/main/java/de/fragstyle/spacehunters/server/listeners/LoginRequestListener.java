package de.fragstyle.spacehunters.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.common.packets.server.Player;
import de.fragstyle.spacehunters.common.packets.server.Disconnected;
import de.fragstyle.spacehunters.common.packets.server.LoginAccepted;
import de.fragstyle.spacehunters.common.packets.client.LoginRequest;
import de.fragstyle.spacehunters.server.PlayerList;
import de.fragstyle.spacehunters.server.ServerPlayer;
import java.util.UUID;

public abstract class LoginRequestListener extends Listener {

  private final PlayerList playerList;

  protected abstract void newPlayer(ServerPlayer serverPlayer);

  protected abstract void removePlayer(UUID uuid);

  public LoginRequestListener(PlayerList playerList) {
    this.playerList = playerList;
  }

  @Override
  public void disconnected(Connection connection) {
    playerList.getByKryoNetClientId(connection.getID()).ifPresent(this::removePlayer);
  }

  @Override
  public void received(Connection connection, Object object) {
    if (object instanceof LoginRequest) {
      LoginRequest loginRequest = (LoginRequest) object;
      Player player = loginRequest.getPlayer();

      if (playerList.isNameAlreadyInUse(player.getName())) {
        connection.sendTCP(Disconnected.PLAYER_NAME_ALREADY_IN_USE);
        connection.close();
        return;
      }

      if (playerList.getPlayers().containsKey(player.getUuid())) {
        connection.sendTCP(Disconnected.PLAYER_ID_ALREADY_IN_USE);
        connection.close();
        return;
      }

      newPlayer(ServerPlayer.fromPlayer(player, connection));

      connection.sendTCP(new LoginAccepted(player));
    }
  }
}
