package de.fragstyle.spacehunters.client.listeners;

import com.esotericsoftware.kryonet.EndPoint;
import de.fragstyle.spacehunters.client.SpaceHuntersClient;

public class Listeners {

  public static void registerListeners(SpaceHuntersClient spaceHuntersClient, EndPoint endPoint) {
    endPoint.addListener(new LoginAcceptedListener());
    endPoint.addListener(new DisconnectedListener());
    endPoint.addListener(new GameSnapshotListener(spaceHuntersClient.getGameSnapshotBuffer()));
  }
}
