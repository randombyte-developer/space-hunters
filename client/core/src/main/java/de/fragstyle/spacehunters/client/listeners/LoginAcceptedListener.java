package de.fragstyle.spacehunters.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.client.SpaceHuntersClientGame;
import de.fragstyle.spacehunters.common.packets.server.LoginAccepted;

public class LoginAcceptedListener extends Listener {

  @Override
  public void received(Connection connection, Object object) {
    super.received(connection, object);

    if (object instanceof LoginAccepted) {
      LoginAccepted loginAccepted = (LoginAccepted) object;

      Gdx.app.log(SpaceHuntersClientGame.TAG, "Connected!");
    }
  }
}
