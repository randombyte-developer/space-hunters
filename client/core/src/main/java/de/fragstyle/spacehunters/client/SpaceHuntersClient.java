package de.fragstyle.spacehunters.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.esotericsoftware.kryonet.Client;
import de.fragstyle.spacehunters.client.listeners.Listeners;
import de.fragstyle.spacehunters.common.KryoUtils;
import de.fragstyle.spacehunters.common.Player;
import de.fragstyle.spacehunters.common.packets.login.LoginRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class SpaceHuntersClient extends ApplicationAdapter {

  private Stage stage;
  private Skin skin;

  private final Client client = new Client();

  private AsyncExecutor asyncExecutor = new AsyncExecutor(3);

  public static final String TAG = "SpaceHuntersClient";

  @Override
  public void create() {
    stage = new Stage();
    skin = new Skin(Gdx.files.internal("uiskin.json"));
    Gdx.input.setInputProcessor(stage);

    prepareAndStartClient();

    ConnectDialog connectDialog = new ConnectDialog("Mit Server verbinden", skin,
        "Player" + MathUtils.random(1, 100), "localhost") {
      @Override
      protected void gotInput(ConnectDialog dialog, String username, String hostname) {
        asyncExecutor.submit(() -> {
          dialog.setStatus("Verbinden...");
          Optional<String> error = connect(username, hostname);
          if (error.isPresent()) {
            dialog.setStatus(error.get());
          } else {
            dialog.setStatus("Verbunden!");
            dialog.hide();
          }

          return null;
        });
      }
    };
    connectDialog.show(stage);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, client.isConnected() ? 1 : 0, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    stage.act();
    stage.draw();
  }

  @Override
  public void dispose() {
    stage.dispose();
    try {
      client.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void prepareAndStartClient() {
    KryoUtils.prepareKryo(client.getKryo());
    Listeners.registerListeners(client);
    client.start();
  }

  /**
   * @return contains the error message or is absent if it connected successfully
   */
  private Optional<String> connect(String username, String host) {
    try {
      client.connect(5000, host, 9880, 9881);
    } catch (IOException e) {
      return Optional.of(e.getMessage());
    }

    client.sendTCP(new LoginRequest(new Player(UUID.randomUUID(), username)));
    return Optional.empty();
  }
}
