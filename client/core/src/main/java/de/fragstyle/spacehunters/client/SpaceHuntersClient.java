package de.fragstyle.spacehunters.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.esotericsoftware.kryonet.Client;
import de.fragstyle.spacehunters.client.listeners.Listeners;
import de.fragstyle.spacehunters.common.KryoUtils;
import de.fragstyle.spacehunters.common.drawing.Frame;
import de.fragstyle.spacehunters.common.drawing.Ship;
import de.fragstyle.spacehunters.common.packets.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.packets.client.InputPacket;
import de.fragstyle.spacehunters.common.packets.client.LoginRequest;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import de.fragstyle.spacehunters.common.packets.server.Player;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class SpaceHuntersClient extends ApplicationAdapter {

  private static final int DISPLAY_GAME_TIME_OFFSET = 100;

  private Stage stage;
  private OrthographicCamera camera;
  private FitViewport viewport;
  private Skin skin;

  private final Client client = new Client();

  private final GameSnapshotBuffer gameSnapshotBuffer = new GameSnapshotBuffer();

  private AsyncExecutor asyncExecutor = new AsyncExecutor(3);

  public static final String TAG = "SpaceHuntersClient";

  @Override
  public void create() {
    camera = new OrthographicCamera();
    viewport = new FitViewport(800, 480, camera);
    stage = new Stage(viewport);

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
            Gdx.app.log(TAG, error.get());
          } else {
            dialog.setStatus("Verbunden!");
            dialog.hide();
          }

          return null;
        });
      }
    };

    connectDialog.show(stage);

    stage.addActor(new Ship());

    Frame frame = new Frame(0, 0, 2000, 1000, 10, Color.RED);
    stage.addActor(frame);

    camera.zoom = 2;

    stage.setDebugAll(true);
  }

  @Override
  public void render() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    handleInput();
    InputPacket inputPacket = createInputPacket();
    if (client.isConnected()) {
      client.sendUDP(inputPacket);
    }

    Optional<GameSnapshot> displaySnapshotTimeOpt = gameSnapshotBuffer
        .getLatestSnapshotBeforeLimit(System.currentTimeMillis() - DISPLAY_GAME_TIME_OFFSET);
    displaySnapshotTimeOpt.ifPresent(gameSnapshot -> {
      gameSnapshot.getShips().values().forEach(ship -> {
        Gdx.app.log(TAG, ship.getX() + ";" + ship.getY());

      });
    });

    camera.update();

    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    viewport.update(width, height, true);
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
    Listeners.registerListeners(this, client);
    client.start();
    client.setKeepAliveTCP(3000);
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

  private InputPacket createInputPacket() {
    short x = 0;
    short y = 0;

    if (Gdx.input.isKeyPressed(Keys.D)) {
      x += 1;
    }
    if (Gdx.input.isKeyPressed(Keys.A)) {
      x -= 1;
    }
    if (Gdx.input.isKeyPressed(Keys.W)) {
      y += 1;
    }
    if (Gdx.input.isKeyPressed(Keys.S)) {
      y -= 1;
    }

    return new InputPacket(x, y);
  }

  private void handleInput() {
    if (Gdx.input.isKeyPressed(Keys.M)) {
      camera.zoom += 0.02;
    }
    if (Gdx.input.isKeyPressed(Keys.N)) {
      camera.zoom -= 0.02;
    }
    if (Gdx.input.isKeyPressed(Keys.LEFT)) {
      camera.translate(-6, 0, 0);
    }
    if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
      camera.translate(6, 0, 0);
    }
    if (Gdx.input.isKeyPressed(Keys.DOWN)) {
      camera.translate(0, -6, 0);
    }
    if (Gdx.input.isKeyPressed(Keys.UP)) {
      camera.translate(0, 6, 0);
    }
    if (Gdx.input.isKeyPressed(Keys.X)) {
      camera.rotate(-1, 0, 0, 1);
    }
    if (Gdx.input.isKeyPressed(Keys.C)) {
      camera.rotate(1, 0, 0, 1);
    }
  }

  public GameSnapshotBuffer getGameSnapshotBuffer() {
    return gameSnapshotBuffer;
  }
}