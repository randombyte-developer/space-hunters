package de.fragstyle.spacehunters.common.drawing;

import static de.fragstyle.spacehunters.common.Constants.DISPLAY_GAME_TIME_OFFSET;

import com.badlogic.gdx.graphics.Color;
import de.fragstyle.spacehunters.common.packets.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class Gamefield extends GameAwareScreenAdapter<SimpleGame> {

  private final GameSnapshotBuffer gameSnapshotBuffer = new GameSnapshotBuffer();

  public Gamefield(SimpleGame game) {
    super(game);

    game.getCamera().zoom = 1.5f;
    game.getCamera().position.set(0, 0, 0);
    game.getStage().setDebugAll(true);

    setupInitial();
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    Map<UUID, OldShip> ships = Arrays.stream(getGame().getStage().getActors().items)
        .filter(actor -> actor instanceof OldShip)
        .map(actor -> (OldShip) actor)
        .collect(Collectors.toMap(ship -> ship.getState().getUuid(), ship -> ship));

    long limit = System.currentTimeMillis() - DISPLAY_GAME_TIME_OFFSET;
    Optional<GameSnapshot> displaySnapshotTimeOpt = gameSnapshotBuffer.getLatestSnapshotBeforeLimit(limit);
    displaySnapshotTimeOpt.ifPresent(gameSnapshot -> {
      gameSnapshot.getShips().values().forEach(ship -> {
        if (!ships.containsKey(ship.getUuid())) {
          getGame().getStage().addActor(new OldShip(ship));
        } else {
          ships.get(ship.getUuid()).setState(ship);
        }
        getGame().getCamera().position.set(ship.getX(), ship.getY(), 5);
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
