package de.fragstyle.spacehunters.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.client.SpaceHuntersClientGame;
import de.fragstyle.spacehunters.common.packets.server.LoginAcceptedPacket;

public class LoginAcceptedListener extends Listener {

  private SpaceHuntersClientGame spaceHuntersClientGame;

  public LoginAcceptedListener(SpaceHuntersClientGame spaceHuntersClientGame) {
    this.spaceHuntersClientGame = spaceHuntersClientGame;
  }

  @Override
  public void received(Connection connection, Object object) {
    super.received(connection, object);

    if (object instanceof LoginAcceptedPacket) {
      LoginAcceptedPacket loginAcceptedPacket = (LoginAcceptedPacket) object;

      Gdx.app.postRunnable(() -> spaceHuntersClientGame.successfullyConnected(loginAcceptedPacket.getPlayer()));
    }
  }
}
