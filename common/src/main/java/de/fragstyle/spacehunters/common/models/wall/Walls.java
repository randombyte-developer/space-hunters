package de.fragstyle.spacehunters.common.models.wall;

import com.badlogic.gdx.math.Vector2;
import de.fragstyle.spacehunters.common.models.EntityState;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Walls {
  public static List<WallState> createFrame(float centerX, float centerY, float width, float height, float thickness) {
    Vector2 topPosition = new Vector2(centerX - (width / 2), centerY + (height / 2) - thickness);
    Vector2 rightPosition = new Vector2(centerX + (width / 2) - thickness, centerY + (height / 2));
    Vector2 bottomPosition = new Vector2(centerX + (width / 2), centerY - (height / 2) + thickness);
    Vector2 leftPosition = new Vector2(centerX - (width / 2) + thickness, centerY - (height / 2));

    WallState top = new WallState(
        new EntityState(UUID.randomUUID(), topPosition, -90 * 0, Vector2.Zero),
        new Vector2(width - thickness, thickness));
    WallState right = new WallState(
        new EntityState(UUID.randomUUID(), rightPosition, -90 * 1, Vector2.Zero),
        new Vector2(height - thickness, thickness));
    WallState bottom = new WallState(
        new EntityState(UUID.randomUUID(), bottomPosition, -90 * 2, Vector2.Zero),
        new Vector2(width - thickness, thickness));
    WallState left = new WallState(
        new EntityState(UUID.randomUUID(), leftPosition, -90 * 3, Vector2.Zero),
        new Vector2(height - thickness, thickness));

    return Arrays.asList(top, right, bottom, left);
  }
}
