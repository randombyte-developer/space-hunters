package de.fragstyle.spacehunters.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.esotericsoftware.kryonet.Client;
import de.fragstyle.spacehunters.client.drawing.JoinServerScreen;
import de.fragstyle.spacehunters.client.listeners.Listeners;
import de.fragstyle.spacehunters.common.KryoUtils;
import de.fragstyle.spacehunters.common.drawing.Gamefield;
import de.fragstyle.spacehunters.common.drawing.SimpleGame;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import java.io.IOException;

public class SpaceHuntersClientGame extends SimpleGame {

  public static final String TAG = "SpaceHuntersClient";

  private Client client;

  private Gamefield gamefield = null;

  @Override
  public void create() {
    super.create();

    client = new Client();
    prepareAndStartClient();

    JoinServerScreen joinServerScreen = new JoinServerScreen(this) {
      @Override
      protected void successfullyConnected() {
        gamefield = new Gamefield(SpaceHuntersClientGame.this);
        setScreen(gamefield);
        newStage(); // todo does this work?
        //joinServerScreen.dispose(); todo make this work
      }
    };
    setScreen(joinServerScreen);
  }

  @Override
  public void render() {
    super.render();

    if (client.isConnected()) {
      client.sendUDP(createInputPacket());
    }
  }

  @Override
  public void dispose() {
    super.dispose();

    try {
      client.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void prepareAndStartClient() {
    KryoUtils.prepareKryo(client.getKryo());
    Listeners.registerListeners(this, client);
    client.setKeepAliveTCP(3000);
    client.start();
  }

  private InputPacket createInputPacket() {
    short rotation = 0;
    short acceleration = 0;

    if (Gdx.input.isKeyPressed(Keys.D)) {
      rotation -= 1;
    }
    if (Gdx.input.isKeyPressed(Keys.A)) {
      rotation += 1;
    }
    if (Gdx.input.isKeyPressed(Keys.W)) {
      acceleration += 1;
    }
    if (Gdx.input.isKeyPressed(Keys.S)) {
      acceleration -= 1;
    }

    return new InputPacket(acceleration, rotation);
  }

  /**
   * Adds the given gameSnapshot. This is a proxy to the {@link Gamefield} if it is present. If no
   * Gamefield is currently displayed, the {@link GameSnapshot} is discarded.
   */
  public void addGameSnapshot(GameSnapshot gameSnapshot) {
    if (gamefield != null) {
      gamefield.getGameSnapshotBuffer().addState(gameSnapshot);
    }
  }

  public Client getClient() {
    return client;
  }
}
