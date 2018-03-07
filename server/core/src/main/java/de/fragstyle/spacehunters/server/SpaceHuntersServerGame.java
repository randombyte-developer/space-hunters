package de.fragstyle.spacehunters.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.kryonet.Server;
import de.fragstyle.spacehunters.common.KryoUtils;
import de.fragstyle.spacehunters.common.drawing.GamefieldScreen;
import de.fragstyle.spacehunters.common.game.Constants;
import de.fragstyle.spacehunters.common.game.GameSnapshot;
import de.fragstyle.spacehunters.common.game.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.game.GameState;
import de.fragstyle.spacehunters.common.game.SimpleGame;
import de.fragstyle.spacehunters.server.listeners.Listeners;
import java.io.IOException;

public class SpaceHuntersServerGame extends SimpleGame {

  private static final int GAME_STATE_TIME_FRAME = 1; // how often a snapshot of the current game state is taken

  private final Server server = new Server();
  private final PlayerList playerList = new PlayerList();

  private GamefieldScreen gamefieldScreen = null;

  private Skin skin;

  private final GameSnapshotBuffer gameSnapshotBuffer = new GameSnapshotBuffer();
  private final GameState gameState = new GameState();

  private int millisSinceLastGameStateSnapshot = 0;

  public static final String TAG = "SpaceHuntersServerGame";

  private Matrix4 debugMatrix;
  private Box2DDebugRenderer debugRenderer;

  private boolean renderingEnabled = true;

  @Override
  public void create() {
    super.create();

    skin = new Skin(Gdx.files.internal("uiskin.json"));

    gamefieldScreen = new GamefieldScreen(this, null);
    setScreen(gamefieldScreen);

    debugMatrix = new Matrix4(getCamera().combined);
    debugMatrix.scale(1f, 1f, 1);
    debugRenderer = new Box2DDebugRenderer();

    KryoUtils.prepareKryo(server.getKryo());
    Listeners.registerListeners(this, server);

    server.start();
    try {
      server.bind(Constants.TCP_PORT, Constants.UDP_PORT);
    } catch (IOException e) {
      e.printStackTrace();
      Gdx.app.exit();
    }
    Gdx.app.log(TAG, "Server started!");
  }

  @Override
  public void render() {
    super.render();

    if (Gdx.input.isKeyJustPressed(Keys.D)) {
      renderingEnabled = !renderingEnabled;
    }
    gamefieldScreen.setRenderingEnabled(renderingEnabled);

    gameState.act(Gdx.graphics.getDeltaTime());

    millisSinceLastGameStateSnapshot += Gdx.graphics.getDeltaTime() * 1000;
    if (millisSinceLastGameStateSnapshot >= GAME_STATE_TIME_FRAME) {
      gameSnapshotBuffer.addState(GameSnapshot.fromGameState(gameState));

      millisSinceLastGameStateSnapshot = 0;
    }

    gameSnapshotBuffer.getLatestSnapshotTime().ifPresent(lastSnapshotTime -> {
      GameSnapshot gameSnapshot = gameSnapshotBuffer.getSnapshots().get(lastSnapshotTime); // guaranteed to never be null
      server.sendToAllUDP(gameSnapshot);
      gamefieldScreen.getGameSnapshotBuffer().addState(gameSnapshot);
    });

    getCamera().position.set(Vector3.Zero);
    getCamera().zoom = 2.7f;

    debugMatrix = new Matrix4(getCamera().combined);
    debugRenderer.render(gameState.getWorld(), debugMatrix);
  }

  @Override
  public void dispose() {
    super.dispose();

    try {
      server.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public PlayerList getPlayerList() {
    return playerList;
  }

  public GameState getGameState() {
    return gameState;
  }
}
