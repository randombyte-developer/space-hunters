package de.fragstyle.spacehunters.common.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Ship extends Actor {

    private Texture texture = new Texture(Gdx.files.internal("ship.png"));

    public Ship() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, 0, 0);
    }
}
