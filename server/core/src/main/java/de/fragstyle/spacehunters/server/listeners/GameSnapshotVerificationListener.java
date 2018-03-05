package de.fragstyle.spacehunters.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.common.packets.client.GameSnapshotReceivedPacket;
import java.util.Optional;
import java.util.UUID;

public abstract class GameSnapshotVerificationListener extends Listener {

  @Override
  public void received(Connection connection, Object object) {
    if (object instanceof GameSnapshotReceivedPacket) {
      getPlayerUuidFromKryoNetClientId(connection.getID()).ifPresent(uuid -> {
        gameSnapshotVerified(uuid, ((GameSnapshotReceivedPacket) object).getSnapshotTime());
      });
    }
  }

  abstract void gameSnapshotVerified(UUID player, long time);

  abstract Optional<UUID> getPlayerUuidFromKryoNetClientId(int id);
}
