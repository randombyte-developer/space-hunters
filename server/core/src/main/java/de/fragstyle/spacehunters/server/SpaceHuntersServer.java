package de.fragstyle.spacehunters.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.kryonet.Server;
import de.fragstyle.spacehunters.common.KryoUtils;
import de.fragstyle.spacehunters.server.listeners.Listeners;
import java.io.IOException;

public class SpaceHuntersServer extends ApplicationAdapter {

  private final Server server = new Server();
  private final PlayerList playerList = new PlayerList();

  private Stage stage;
  private Skin skin;

  private Label infoLabel;

  public static final String TAG = "SpaceHuntersServer";

  @Override
  public void create() {
    stage = new Stage();
    skin = new Skin(Gdx.files.internal("uiskin.json"));
    Gdx.input.setInputProcessor(stage);

    infoLabel = new Label("Connected clients: 0", skin);
    stage.addActor(infoLabel);

    KryoUtils.prepareKryo(server.getKryo());
    Listeners.registerListeners(this, server);

    server.start();
    try {
      server.bind(9880, 9881); // todo make configurable
    } catch (IOException e) {
      e.printStackTrace();
      Gdx.app.exit();
    }
    Gdx.app.log(TAG, "Server started!");
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    infoLabel.setText("Connected clients: " + server.getConnections().length);

    stage.act();
    stage.draw();
  }

  @Override
  public void dispose() {
    stage.dispose();
    try {
      server.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public PlayerList getPlayerList() {
    return playerList;
  }
}