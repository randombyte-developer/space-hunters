package de.fragstyle.spacehunters.common.drawing;

import static de.fragstyle.spacehunters.common.Constants.DISPLAY_GAME_TIME_OFFSET;

import com.badlogic.gdx.graphics.Color;
import com.sun.istack.internal.Nullable;
import de.fragstyle.spacehunters.common.models.ship.ShipState;
import de.fragstyle.spacehunters.common.packets.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import de.fragstyle.spacehunters.common.packets.server.Player;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GamefieldScreen extends GameAwareScreenAdapter<SimpleGame> {

  private final Player viewer;

  private final GameSnapshotBuffer gameSnapshotBuffer = new GameSnapshotBuffer();

  //private final Map<UUID, ShipActor> shipActors = new HashMap<>();

  public GamefieldScreen(SimpleGame game, @Nullable Player viewer) {
    super(game);
    this.viewer = viewer;

    game.getCamera().zoom = 1.5f;
    game.getCamera().position.set(0, 0, 0);
    game.getStage().setDebugAll(true);

    setupInitial();
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    Map<UUID, ShipActor> shipActors = Arrays.stream(getGame().getStage().getActors().items)
        .filter(actor -> actor instanceof ShipActor)
        .map(actor -> (ShipActor) actor)
        .collect(Collectors.toMap(ship -> ship.getShipState().getUuid(), ship -> ship));

    // display a GameSnapshot from some time ago(DISPLAY_GAME_TIME_OFFSET in ms)
    long limit = System.currentTimeMillis() - DISPLAY_GAME_TIME_OFFSET;
    Optional<GameSnapshot> displaySnapshotTimeOpt = gameSnapshotBuffer.getLatestSnapshotBeforeLimit(limit);
    displaySnapshotTimeOpt.ifPresent(gameSnapshot -> {

      Map<UUID, ShipState> shipStates = gameSnapshot.getShips();
      shipStates.forEach((uuid, state) -> {

        if (!shipActors.containsKey(uuid)) {
          getGame().getStage().addActor(new ShipActor(state));
          //this.shipActors.put(uuid, new ShipActor(state));
        } else {
          shipActors.get(uuid).setShipState(state);
        }

        if (viewer != null) {
          if (viewer.getUuid().equals(uuid)) {
            getGame().getCamera().position.set(state.getX(), state.getY(), 0);
          }
        }
      });
    });
  }

  @Override
  public void dispose() {
    getGame().dispose();
  }

  private void setupInitial() {
    Frame frame = new Frame(0, 0, 2000, 1000, 10, Color.RED);
    getGame().getStage().addActor(frame);
  }

  public GameSnapshotBuffer getGameSnapshotBuffer() {
    return gameSnapshotBuffer;
  }

  public Optional<Player> getViewer() {
    return Optional.ofNullable(viewer);
  }
}
