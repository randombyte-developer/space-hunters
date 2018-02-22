package de.fragstyle.spacehunters.common.drawing.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import de.fragstyle.spacehunters.common.models.ship.ShipState;

public class ShipActor extends BetterActor {

    private ShipState shipState;

    private Texture texture = new Texture(Gdx.files.internal("ship.png"));

    public ShipActor(ShipState shipState) {
        this.shipState = shipState;
        setSize(texture.getWidth(), texture.getHeight());
        //setOrigin(getWidth() / 2, getHeight() / 2);
        act(0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // wtf, I just want to change the rotation
        batch.draw(texture,
            getX(), getY(),
            shipState.getOriginX(), shipState.getOriginY(),
            texture.getWidth(), texture.getHeight(),
            1, 1,
            getRotation(),
            0, 0,
            texture.getWidth(), texture.getHeight(),
            false, false);
    }

    public ShipState getShipState() {
        return shipState;
    }

    public void setShipState(ShipState shipState) {
        this.shipState = shipState;

        setPosition(shipState.getX(), shipState.getY());
        setRotation(shipState.getRotation());
        setOrigin(shipState.getOriginX(), shipState.getOriginY());
    }
}
