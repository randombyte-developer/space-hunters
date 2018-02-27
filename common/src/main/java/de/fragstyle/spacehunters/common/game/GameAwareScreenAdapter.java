package de.fragstyle.spacehunters.common.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class GameAwareScreenAdapter<T extends SimpleGame> extends ScreenAdapter {

  private T game;

  public GameAwareScreenAdapter(T game) {
    this.game = game;
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    game.getCamera().update();

    game.getStage().act(delta);
    game.getStage().draw();
  }

  public T getGame() {
    return game;
  }
}
