package de.fragstyle.spacehunters.client.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import de.fragstyle.spacehunters.client.SpaceHuntersClient;
import de.fragstyle.spacehunters.common.packets.login.Disconnected;

public class DisconnectedListener extends Listener {

  @Override
  public void disconnected(Connection connection) {
    super.disconnected(connection);

    Gdx.app.log(SpaceHuntersClient.TAG, "The server was closed!");
  }

  @Override
  public void received(Connection connection, Object object) {
    super.received(connection, object);

    if (object instanceof Disconnected) {
      Disconnected disconnected = (Disconnected) object;
      String reason = disconnected.getReason();

      Gdx.app.log(SpaceHuntersClient.TAG, "Disconnected: '" + reason + "'");
    }
  }
}