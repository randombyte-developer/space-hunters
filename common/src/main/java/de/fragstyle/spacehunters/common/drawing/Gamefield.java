package de.fragstyle.spacehunters.common.drawing;

import static de.fragstyle.spacehunters.common.Constants.DISPLAY_GAME_TIME_OFFSET;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import de.fragstyle.spacehunters.common.packets.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import java.util.Optional;

public class Gamefield extends GameAwareScreenAdapter<SimpleGame> {

  private final GameSnapshotBuffer gameSnapshotBuffer = new GameSnapshotBuffer();

  public Gamefield(SimpleGame game) {
    super(game);

    game.getCamera().zoom = 2;
    game.getStage().setDebugAll(true);

    setupInitial();
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    Optional<GameSnapshot> displaySnapshotTimeOpt = gameSnapshotBuffer
        .getLatestSnapshotBeforeLimit(System.currentTimeMillis() - DISPLAY_GAME_TIME_OFFSET);
    displaySnapshotTimeOpt.ifPresent(gameSnapshot -> {
      gameSnapshot.getShips().values().forEach(ship -> {
        Gdx.app.log("", ship.getX() + ";" + ship.getY());

      });
    });
  }

  @Override
  public void dispose() {

  }

  private void setupInitial() {
    Frame frame = new Frame(0, 0, 2000, 1000, 10, Color.RED);
    getGame().getStage().addActor(frame);
  }

  public GameSnapshotBuffer getGameSnapshotBuffer() {
    return gameSnapshotBuffer;
  }
}
