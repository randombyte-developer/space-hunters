package de.fragstyle.spacehunters.client.listeners;

import com.esotericsoftware.kryonet.EndPoint;

public class Listeners {
  public static void registerListeners(EndPoint endPoint) {
    endPoint.addListener(new LoginAcceptedListener());
    endPoint.addListener(new DisconnectedListener());
  }
}
