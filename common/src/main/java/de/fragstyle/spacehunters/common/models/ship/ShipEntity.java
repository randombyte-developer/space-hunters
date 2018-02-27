package de.fragstyle.spacehunters.common.models.ship;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.models.Entity;
import de.fragstyle.spacehunters.common.models.EntityState;

public class ShipEntity extends Entity {

  public ShipEntity(ShipState shipState, World world) {
    super(shipState, world);

    getBody().setAngularDamping(1);
  }

  protected Body loadBody(World world, EntityState entityState) {
    ShipState shipState = (ShipState) entityState;

    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("ship.json"));

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    Body body = world.createBody(bodyDef);
    body.setTransform(shipState.getPosition().x, shipState.getPosition().y, shipState.getRotation() * MathUtils.degreesToRadians);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 1f;
    fixtureDef.friction = 0.5f;
    fixtureDef.restitution = 0.3f;

    loader.attachFixture(body, shipState.getEntityType().getId(), fixtureDef, 100);

    //this.setOrigin(loader.getOrigin(ID, 100));

    return body;
  }
}