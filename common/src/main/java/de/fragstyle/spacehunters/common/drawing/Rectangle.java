package de.fragstyle.spacehunters.common.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Rectangle extends BetterActor {

  private ShapeRenderer shapeRenderer = new ShapeRenderer();
  private boolean projectionMatrixSet = false;

  private final float width;
  private final float height;
  private Color color;

  public Rectangle(float width, float height, Color color) {
    this.width = width;
    this.height = height;
    this.color = color;

    //setOrigin(width / 2, height / 2);
    setBounds(0, 0, width, height);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    batch.end();

    if (!projectionMatrixSet) {
      shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    }
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(color);
    shapeRenderer.rect(getX(), getY(), getOriginX(), getOriginY(), width, height, getScaleX(), getScaleY(), getRotation());

    // todo remove
    shapeRenderer.circle(0, 0, 10);
    shapeRenderer.circle(100, 0, 10);
    shapeRenderer.circle(200, 0, 10);
    shapeRenderer.circle(300, 0, 10);
    shapeRenderer.circle(0, 100, 10);
    shapeRenderer.circle(0, 200, 10);
    shapeRenderer.circle(0, 300, 10);
    shapeRenderer.circle(-100, 0, 10);
    shapeRenderer.circle(-200, 0, 10);
    shapeRenderer.circle(-300, 0, 10);
    shapeRenderer.circle(0, -100, 10);
    shapeRenderer.circle(0, -200, 10);
    shapeRenderer.circle(0, -300, 10);

    shapeRenderer.end();

    batch.begin();
  }

  private Vector2 getStartPosition() {
    return new Vector2(getX() - getXDistanceFromCenter(), getY() - getYDistanceFromCenter());
  }

  private Vector2 getEndPosition() {
    return new Vector2(getX() + getXDistanceFromCenter(), getY() + getYDistanceFromCenter());
  }

  private float getXDistanceFromCenter() {
    return (float) Math.sin(getRotation()) * (height / 2);
  }

  private float getYDistanceFromCenter() {
    return (float) Math.cos(getRotation()) * (height / 2);
  }
}
