package de.fragstyle.spacehunters.common.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class SimpleGame extends Game {
  private Stage stage;
  private ShapeRenderer shapeRenderer;

  private OrthographicCamera camera;
  private ScalingViewport viewport;

  private Skin skin;

  @Override
  public void create() {
    shapeRenderer  = new ShapeRenderer();
    camera = new OrthographicCamera();
    viewport = new FitViewport(800, 480, camera);
    stage = new Stage(viewport);

    Gdx.input.setInputProcessor(stage);

    skin = new Skin(Gdx.files.internal("uiskin.json"));
  }

  @Override
  public void resize(int width, int height) {
    super.resize(width, height);

    viewport.update(width, height, true);
  }

  @Override
  public void dispose() {
    super.dispose();

    stage.dispose();
  }

  public void newStage() {
    stage.dispose();
    stage = new Stage(viewport);
  }

  public Stage getStage() {
    return stage;
  }

  public ShapeRenderer getShapeRenderer() {
    return shapeRenderer;
  }

  public OrthographicCamera getCamera() {
    return camera;
  }

  public ScalingViewport getViewport() {
    return viewport;
  }

  public SpriteBatch getStageSpriteBatch() {
    return ((SpriteBatch) stage.getBatch());
  }

  public Skin getSkin() {
    return skin;
  }
}
