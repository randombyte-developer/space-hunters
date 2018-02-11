package de.fragstyle.spacehunters.common.drawing;

import static de.fragstyle.spacehunters.common.Constants.DISPLAY_GAME_TIME_OFFSET;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.models.ship.ShipState;
import de.fragstyle.spacehunters.common.packets.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;

import de.fragstyle.spacehunters.common.packets.server.Player;
import java.util.*;
import java.util.stream.Collectors;

public class GamefieldScreen extends GameAwareScreenAdapter<SimpleGame> {

  private final Player self;

  private final GameSnapshotBuffer gameSnapshotBuffer = new GameSnapshotBuffer();
  private final World world;

  Matrix4 debugMatrix;
  Box2DDebugRenderer debugRenderer;

  private final Map<UUID, ShipActor> shipActors = new HashMap<>();

  public GamefieldScreen(SimpleGame game, Player self) {
    super(game);
    this.self = self;

    world = new World(Vector2.Zero, false);

    debugMatrix = new Matrix4(getGame().getCamera().combined);
    debugMatrix.scale(1, 1, 1f);
    debugRenderer = new Box2DDebugRenderer();

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
          // getGame().getStage().addActor(new ShipActor(ship));
          this.shipActors.put(uuid, new ShipActor(state));
        } else {
          shipActors.get(uuid).setShipState(state);
        }

        if (self.getUuid().equals(uuid)) {
          getGame().getCamera().position.set(state.getX(), state.getY(), 0);
        }
      });
    });

    debugRenderer.render(world, debugMatrix);
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
