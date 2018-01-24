package de.fragstyle.spacehunters.server.listeners;

import com.esotericsoftware.kryonet.EndPoint;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.server.ShipPacket;
import de.fragstyle.spacehunters.server.ServerPlayer;
import de.fragstyle.spacehunters.server.SpaceHuntersServer;
import java.util.Optional;
import java.util.UUID;

public class Listeners {

  public static void registerListeners(SpaceHuntersServer spaceHuntersServer, EndPoint endPoint) {
    endPoint.addListener(new LoginRequestListener(spaceHuntersServer.getPlayerList()) {
      @Override
      protected void newPlayer(ServerPlayer serverPlayer) {
        spaceHuntersServer.getPlayerList().add(serverPlayer);
        spaceHuntersServer.getShipsState().addShip(new ShipPacket(serverPlayer.getUuid(), 0, 0, 0, 0, 0));
      }
    });

    endPoint.addListener(new InputListener() {
      @Override
      protected Optional<UUID> getPlayerUuidFromKryoNetClientId(int id) {
        return spaceHuntersServer.getPlayerList().getByKryoNetClientId(id);
      }

      @Override
      protected void receivedInputPacket(UUID playerUuid, InputPacket inputPacket) {
        spaceHuntersServer.getShipsState().handleInputPacket(playerUuid, inputPacket);
      }
    });
  }
}
