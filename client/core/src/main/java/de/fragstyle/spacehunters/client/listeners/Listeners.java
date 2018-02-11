package de.fragstyle.spacehunters.client.listeners;

import com.esotericsoftware.kryonet.EndPoint;
import de.fragstyle.spacehunters.client.SpaceHuntersClientGame;

public class Listeners {

  public static void registerListeners(SpaceHuntersClientGame spaceHuntersClientGame, EndPoint endPoint) {
    endPoint.addListener(new LoginAcceptedListener(spaceHuntersClientGame));
    endPoint.addListener(new DisconnectedListener());
    endPoint.addListener(new GameSnapshotListener(spaceHuntersClientGame));
  }
}
