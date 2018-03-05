package de.fragstyle.spacehunters.server.listeners;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.EndPoint;
import de.fragstyle.spacehunters.common.models.entities.EntityState;
import de.fragstyle.spacehunters.common.models.entities.ship.ShipEntity;
import de.fragstyle.spacehunters.common.models.entities.ship.ShipState;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.server.ServerPlayer;
import de.fragstyle.spacehunters.server.SpaceHuntersServerGame;
import java.util.Optional;
import java.util.UUID;

public class Listeners {

  public static void registerListeners(SpaceHuntersServerGame spaceHuntersServerGame, EndPoint endPoint) {
    endPoint.addListener(new LoginRequestListener(spaceHuntersServerGame.getPlayerList()) {
      @Override
      protected void newPlayer(ServerPlayer serverPlayer) {
        spaceHuntersServerGame.getPlayerList().add(serverPlayer);
        ShipState shipState = new ShipState(new EntityState(serverPlayer.getUuid(), Vector2.Zero, 0, new Vector2(32, 32)));
        spaceHuntersServerGame.getGameState().addEntity(new ShipEntity(shipState, spaceHuntersServerGame.getGameState().getWorld()));
      }

      @Override
      protected void removePlayer(UUID uuid) {
        spaceHuntersServerGame.getPlayerList().remove(uuid);
        spaceHuntersServerGame.getGameState().removeShip(uuid);
      }
    });

    endPoint.addListener(new InputListener() {
      @Override
      protected Optional<UUID> getPlayerUuidFromKryoNetClientId(int id) {
        return spaceHuntersServerGame.getPlayerList().getByKryoNetClientId(id);
      }

      @Override
      protected void receivedInputPacket(UUID playerUuid, InputPacket inputPacket) {
        spaceHuntersServerGame.getGameState().handleInputPacket(playerUuid, inputPacket);
      }
    });
  }
}
