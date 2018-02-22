package de.fragstyle.spacehunters.common.models.ship;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.models.Entity;
import java.util.UUID;

public class ShipEntity extends Entity {

  private static final String ID = "ship";

  public ShipEntity(UUID uuid, World world) {
    super(uuid, world);

    loadBody(world);
    getBody().setAngularDamping(1);
  }

  @Override
  public ShipState getState() {
    return new ShipState(super.getState());
  }

  private void loadBody(World world) {
    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("ship.json"));

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    Body body = world.createBody(bodyDef);
    body.setTransform(0, 0, 0);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 1f;
    fixtureDef.friction = 0.5f;
    fixtureDef.restitution = 0.3f;

    loader.attachFixture(body, ID, fixtureDef, 100);

    this.setBody(body);
    this.setOrigin(loader.getOrigin(ID, 100));
  }
}
