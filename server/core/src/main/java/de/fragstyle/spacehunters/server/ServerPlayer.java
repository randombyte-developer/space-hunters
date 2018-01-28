package de.fragstyle.spacehunters.server;

import com.esotericsoftware.kryonet.Connection;
import de.fragstyle.spacehunters.common.packets.server.Player;
import java.util.UUID;

public class ServerPlayer extends Player {

  private Connection connection;

  public ServerPlayer(UUID uuid, String name, Connection connection) {
    super(uuid, name);

    this.connection = connection;
  }

  public Connection getConnection() {
    return connection;
  }

  public static ServerPlayer fromPlayer(Player player, Connection connection) {
    return new ServerPlayer(player.getUuid(), player.getName(), connection);
  }
}
