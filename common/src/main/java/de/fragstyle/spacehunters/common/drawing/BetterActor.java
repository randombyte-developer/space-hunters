package de.fragstyle.spacehunters.common.drawing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BetterActor extends Actor {

  public void setPosition(Vector2 position) {
    setPosition(position.x, position.y);
  }
}
