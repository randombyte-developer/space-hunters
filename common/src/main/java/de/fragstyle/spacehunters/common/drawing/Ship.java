package de.fragstyle.spacehunters.common.drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import de.fragstyle.spacehunters.common.packets.server.ShipStatePacket;

public class Ship extends BetterActor {

    private ShipStatePacket state;

    private Texture texture = new Texture(Gdx.files.internal("ship.png"));

    public Ship(ShipStatePacket shipStatePacket) {
        this.state = shipStatePacket;
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        act(0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setPosition(state.getX(), state.getY());
        setRotation(state.getRotation());
    }

    public ShipStatePacket getState() {
        return state;
    }

    public void setState(ShipStatePacket state) {
        this.state = state;
        // todo start action?
    }
}
