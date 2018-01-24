package de.fragstyle.spacehunters.server.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.server.SpaceHuntersServer;
import java.util.Optional;
import java.util.UUID;

public abstract class InputListener extends Listener {

  @Override
  public void received(Connection connection, Object object) {
    if (object instanceof InputPacket) {
      InputPacket inputPacket = (InputPacket) object;

      Optional<UUID> playerUuidOpt = getPlayerUuidFromKryoNetClientId(connection.getID());
      if (!playerUuidOpt.isPresent()) {
        Gdx.app.log(SpaceHuntersServer.TAG,
            "Ignoring InputPacket from connection '" + connection.getID() + "' since it's not mapped to any online player!");
        return;
      }

      receivedInputPacket(playerUuidOpt.get(), inputPacket);
    }
  }

  protected abstract Optional<UUID> getPlayerUuidFromKryoNetClientId(int id);

  protected abstract void receivedInputPacket(UUID playerUuid, InputPacket inputPacket);
}
