package de.fragstyle.spacehunters.server.listeners;

import com.esotericsoftware.kryonet.EndPoint;
import de.fragstyle.spacehunters.server.SpaceHuntersServer;

public class Listeners {

  public static void registerListeners(SpaceHuntersServer spaceHuntersServer, EndPoint endPoint) {
    endPoint.addListener(new LoginRequestListener(spaceHuntersServer.getPlayerList()));
  }
}
