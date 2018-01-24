package de.fragstyle.spacehunters.common.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Frame extends Group {

  private final float centerX;
  private final float centerY;
  private final float width;
  private final float height;
  private final float thickness;
  private Color color;

  public Frame(float centerX, float centerY, float width, float height, float thickness, Color color) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.width = width;
    this.height = height;
    this.thickness = thickness;
    this.color = color;

    Vector2 topPosition = new Vector2(centerX - (width / 2), centerY + (height / 2) - thickness);
    Vector2 rightPosition = new Vector2(centerX + (width / 2) - thickness, centerY + (height / 2));
    Vector2 bottomPosition = new Vector2(centerX + (width / 2), centerY - (height / 2) + thickness);
    Vector2 leftPosition = new Vector2(centerX - (width / 2) + thickness, centerY - (height / 2));

    Rectangle topRectangle = new Rectangle(width - thickness, thickness, color);
    Rectangle rightRectangle = new Rectangle(width - thickness, thickness, color);
    Rectangle bottomRectangle = new Rectangle(width - thickness, thickness, color);
    Rectangle leftRectangle = new Rectangle(width - thickness, thickness, color);

    topRectangle.setPosition(topPosition);
    rightRectangle.setPosition(rightPosition);
    bottomRectangle.setPosition(bottomPosition);
    leftRectangle.setPosition(leftPosition);

    topRectangle.setRotation(-90 * 0);
    rightRectangle.setRotation(-90 * 1);
    bottomRectangle.setRotation(-90 * 2);
    leftRectangle.setRotation(-90 * 3);

    addActor(topRectangle);
    addActor(rightRectangle);
    addActor(bottomRectangle);
    addActor(leftRectangle);
  }
}
