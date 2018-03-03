package de.fragstyle.spacehunters.common.drawing;

import static de.fragstyle.spacehunters.common.game.Constants.DISPLAY_GAME_TIME_OFFSET;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.sun.istack.internal.Nullable;
import de.fragstyle.spacehunters.common.game.GameAwareScreenAdapter;
import de.fragstyle.spacehunters.common.game.SimpleGame;
import de.fragstyle.spacehunters.common.models.EntityState;
import de.fragstyle.spacehunters.common.models.wall.WallState;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshot;
import de.fragstyle.spacehunters.common.packets.server.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.packets.server.Player;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GamefieldScreen extends GameAwareScreenAdapter<SimpleGame> {

  private final Player viewer;

  private final GameSnapshotBuffer gameSnapshotBuffer = new GameSnapshotBuffer();

  //private final Map<UUID, ShipActor> shipActors = new HashMap<>();

  public GamefieldScreen(SimpleGame game, @Nullable Player viewer) {
    super(game);
    this.viewer = viewer;

    game.getCamera().zoom = 2f;
    game.getCamera().position.set(Vector3.Zero);
    game.getCamera().update();
    game.getStage().setDebugAll(true);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    // display a GameSnapshot from some time ago(DISPLAY_GAME_TIME_OFFSET in ms)
    long limit = System.currentTimeMillis() - DISPLAY_GAME_TIME_OFFSET;
    Optional<GameSnapshot> displaySnapshotTimeOpt = gameSnapshotBuffer.getLatestSnapshotBeforeLimit(limit);
    displaySnapshotTimeOpt.ifPresent(gameSnapshot -> {

      Map<UUID, EntityState> shipStates = gameSnapshot.getEntityStates();
      shipStates.forEach((uuid, state) -> {

        switch (state.getEntityType()) {
          case SHIP:
            SpriteBatch spriteBatch = getGame().getStageSpriteBatch();
            spriteBatch.begin();

            spriteBatch.draw(Textures.SHIP,
                state.getPosition().x, state.getPosition().y,
                state.getOrigin().x, state.getOrigin().y,
                Textures.SHIP.getWidth(), Textures.SHIP.getHeight(),
                1, 1,
                state.getRotation(),
                0, 0,
                Textures.SHIP.getWidth(), Textures.SHIP.getHeight(),
                false, false);

            spriteBatch.end();

            break;

          case WALL:
            WallState wallState = (WallState) state;

            ShapeRenderer shapeRenderer = getGame().getShapeRenderer();
            shapeRenderer.setProjectionMatrix(getGame().getStage().getBatch().getProjectionMatrix());
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(Color.GOLD);
            shapeRenderer.rect(wallState.getPosition().x - wallState.getDimensions().x / 2f, wallState.getPosition().y - wallState.getDimensions().y / 2f,
                wallState.getOrigin().x, wallState.getOrigin().y,
                wallState.getDimensions().x, wallState.getDimensions().y,
                1, 1, wallState.getRotation());
            shapeRenderer.end();

            break;

          default:
            throw new IllegalStateException("Unknown EntityType '" + state.getEntityType().getId() + "'!");
        }


        /*if (!shipActors.containsKey(uuid)) {
          getGame().getStage().addActor(new ShipActor(state));
          //this.shipActors.put(uuid, new ShipActor(state));
        } else {
          shipActors.get(uuid).setShipState(state);
        }*/

        if (viewer != null) {
          if (viewer.getUuid().equals(uuid)) {
            getGame().getCamera().position.set(state.getPosition(), 0);
          }
        }
      });
    });
  }

  @Override
  public void dispose() {
    getGame().dispose();
  }

  public GameSnapshotBuffer getGameSnapshotBuffer() {
    return gameSnapshotBuffer;
  }

  public Optional<Player> getViewer() {
    return Optional.ofNullable(viewer);
  }
}
