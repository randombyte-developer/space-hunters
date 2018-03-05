package de.fragstyle.spacehunters.client.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.client.SpaceHuntersClientGame;
import de.fragstyle.spacehunters.common.game.GameSnapshot;
import de.fragstyle.spacehunters.common.packets.client.GameSnapshotReceivedPacket;

public class GameSnapshotListener extends Listener {

  private SpaceHuntersClientGame spaceHuntersClientGame;

  public GameSnapshotListener(SpaceHuntersClientGame spaceHuntersClientGame) {
    this.spaceHuntersClientGame = spaceHuntersClientGame;
  }

  @Override
  public void received(Connection connection, Object object) {
    if (object instanceof GameSnapshot) {
      GameSnapshot gameSnapshot = (GameSnapshot) object;
      spaceHuntersClientGame.addGameSnapshot(gameSnapshot);
      connection.sendUDP(new GameSnapshotReceivedPacket(gameSnapshot.getTime()));
    }
  }
}
