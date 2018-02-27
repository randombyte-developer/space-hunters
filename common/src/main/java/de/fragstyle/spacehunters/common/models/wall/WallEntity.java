package de.fragstyle.spacehunters.common.models.wall;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.models.Entity;
import de.fragstyle.spacehunters.common.models.EntityState;

public class WallEntity extends Entity {

  public WallEntity(WallState wallState, World world) {
    super(wallState, world);
  }

  @Override
  public WallState getState() {
    return (WallState) super.getState();
  }

  protected Body loadBody(World world, EntityState entityState) {
    WallState wallState = (WallState) entityState;

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.StaticBody;
    Body body = world.createBody(bodyDef);
    body.setTransform(wallState.getPosition().x, wallState.getPosition().y, wallState.getRotation() * MathUtils.degreesToRadians);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 1f;
    fixtureDef.friction = 0.5f;
    fixtureDef.restitution = 0;

    PolygonShape polygonShape = new PolygonShape();
    polygonShape.setAsBox(wallState.getDimensions().x, wallState.getDimensions().y);
    fixtureDef.shape = polygonShape;

    body.createFixture(fixtureDef);

    return body;
  }
}
