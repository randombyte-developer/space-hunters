package de.fragstyle.spacehunters.common.models.entities.wall;

import com.badlogic.gdx.math.Vector2;
import de.fragstyle.spacehunters.common.models.entities.EntityState;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Walls {
  public static List<WallState> createFrame(float centerX, float centerY, float width, float height, float thickness) {
    float halfWidth = width / 2f;
    float halfHeight= height/ 2f;
    float halfThickness = thickness / 2f;

    // all position refer to the center of the wall
    Vector2 topPosition = new Vector2(centerX, centerY + halfHeight + halfThickness);
    Vector2 rightPosition = new Vector2(centerX + halfWidth + halfThickness - thickness, centerY);
    Vector2 bottomPosition = new Vector2(centerX, centerY - halfHeight - halfThickness);
    Vector2 leftPosition = new Vector2(centerX - halfWidth - halfThickness + thickness, centerY);

    Vector2 horizontalDimensions = new Vector2(width, thickness);
    Vector2 verticalDimensions = new Vector2(thickness, height);

    WallState top = new WallState(
        new EntityState(UUID.randomUUID(), topPosition, 0, Vector2.Zero),
        horizontalDimensions);
    WallState right = new WallState(
        new EntityState(UUID.randomUUID(), rightPosition, 0, Vector2.Zero),
        verticalDimensions);
    WallState bottom = new WallState(
        new EntityState(UUID.randomUUID(), bottomPosition, 0, Vector2.Zero),
        horizontalDimensions);
    WallState left = new WallState(
        new EntityState(UUID.randomUUID(), leftPosition, 0, Vector2.Zero),
        verticalDimensions);

    return Arrays.asList(top, right, bottom, left);
  }
}
