package de.fragstyle.spacehunters.client.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.common.packets.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;

public class GameSnapshotListener extends Listener {

  private GameSnapshotBuffer gameSnapshotBuffer;

  public GameSnapshotListener(GameSnapshotBuffer gameSnapshotBuffer) {
    this.gameSnapshotBuffer = gameSnapshotBuffer;
  }

  @Override
  public void received(Connection connection, Object object) {
    if (object instanceof GameSnapshot) {
      GameSnapshot gameSnapshot = (GameSnapshot) object;
      gameSnapshotBuffer.addState(gameSnapshot);
    }
  }
}
