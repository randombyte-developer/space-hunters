package de.fragstyle.spacehunters.common.drawing;

import static de.fragstyle.spacehunters.common.drawing.Textures.BACKGROUND_BACK;
import static de.fragstyle.spacehunters.common.game.Constants.DISPLAY_GAME_TIME_OFFSET;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.sun.istack.internal.Nullable;
import de.fragstyle.spacehunters.common.game.GameAwareScreenAdapter;
import de.fragstyle.spacehunters.common.game.GameSnapshot;
import de.fragstyle.spacehunters.common.game.GameSnapshotBuffer;
import de.fragstyle.spacehunters.common.game.SimpleGame;
import de.fragstyle.spacehunters.common.models.Player;
import de.fragstyle.spacehunters.common.models.entities.EntityState;
import de.fragstyle.spacehunters.common.models.entities.wall.WallState;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GamefieldScreen extends GameAwareScreenAdapter<SimpleGame> {

  private final Player viewer;

  private final GameSnapshotBuffer gameSnapshotBuffer = new GameSnapshotBuffer();

  private boolean renderingEnabled = true;

  public GamefieldScreen(SimpleGame game, @Nullable Player viewer) {
    super(game);
    this.viewer = viewer;

    Background.generateBackgroundTextures(0.001f);

    game.getCamera().zoom = 1f;
    game.getCamera().position.set(Vector3.Zero);
    game.getCamera().update();
    game.getStage().setDebugAll(true);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    if (!renderingEnabled) return;

    // draw background
    SpriteBatch spriteBatch = getGame().getStageSpriteBatch();
    spriteBatch.begin();
    for (int x = -1500; x < 1500; x += BACKGROUND_BACK.getWidth()) {
      for (int y = -1000; y < 1000; y += BACKGROUND_BACK.getHeight()) {
        spriteBatch.draw(BACKGROUND_BACK, x, y, 0, 0, BACKGROUND_BACK.getWidth(), BACKGROUND_BACK.getHeight());
      }
    }
    spriteBatch.end();

    // display a GameSnapshot from some time ago(DISPLAY_GAME_TIME_OFFSET in ms)
    long limit = System.currentTimeMillis() - DISPLAY_GAME_TIME_OFFSET;
    Optional<GameSnapshot> displaySnapshotTimeOpt = gameSnapshotBuffer.getLatestSnapshotBeforeLimit(limit);
    displaySnapshotTimeOpt.ifPresent(gameSnapshot -> {

      Map<UUID, EntityState> shipStates = gameSnapshot.getEntityStates();
      shipStates.forEach((uuid, state) -> {

        switch (state.getEntityType()) {
          case SHIP:
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
            shapeRenderer.rect(
                wallState.getPosition().x - wallState.getDimensions().x / 2f, wallState.getPosition().y - wallState.getDimensions().y / 2f,
                wallState.getOrigin().x, wallState.getOrigin().y,
                wallState.getDimensions().x, wallState.getDimensions().y,
                1, 1, wallState.getRotation());
            shapeRenderer.end();

            break;

          default:
            throw new IllegalStateException("Unknown EntityType '" + state.getEntityType().getId() + "'!");
        }

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

  public void setRenderingEnabled(boolean renderingEnabled) {
    this.renderingEnabled = renderingEnabled;
  }
}
