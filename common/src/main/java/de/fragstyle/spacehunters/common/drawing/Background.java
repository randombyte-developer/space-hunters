package de.fragstyle.spacehunters.common.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.math.MathUtils;

public class Background {

  public static final int BACKGROUND_WIDTH = 512;
  public static final int BACKGROUND_HEIGHT = 512;

  public static void generateBackgroundTextures(float density) {
    Pixmap pixmap = new Pixmap(BACKGROUND_WIDTH, BACKGROUND_HEIGHT, Format.RGBA8888);
    pixmap.setColor(Color.WHITE);

    for (int x = 0; x < BACKGROUND_WIDTH; x++) {
      for (int y = 0; y < BACKGROUND_HEIGHT; y++) {
        if (MathUtils.randomBoolean(density)) {
          pixmap.drawPixel(x, y);
        }
      }
    }

    Textures.BACKGROUND_BACK = new Texture(pixmap);
    Textures.BACKGROUND_BACK.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
  }
}
