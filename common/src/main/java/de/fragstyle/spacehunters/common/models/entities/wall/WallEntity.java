package de.fragstyle.spacehunters.common.models.entities.wall;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.models.entities.Entity;
import de.fragstyle.spacehunters.common.models.entities.EntityState;

public class WallEntity extends Entity {

  public WallEntity(WallState wallState, World world) {
    super(wallState, world);
  }

  @Override
  public WallState getState() {
    WallState wallState = (WallState) super.getState();
    wallState.setOrigin(new Vector2(wallState.getDimensions().x / 2f, wallState.getDimensions().y / 2f));
    return wallState;
  }

  protected Body loadBody(World world, EntityState entityState) {
    WallState wallState = (WallState) entityState;

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.StaticBody;
    Body body = world.createBody(bodyDef);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 1f;
    fixtureDef.friction = 0.5f;
    fixtureDef.restitution = 0;

    PolygonShape polygonShape = new PolygonShape();
    polygonShape.setAsBox(wallState.getDimensions().x / 2f, wallState.getDimensions().y / 2f);
    fixtureDef.shape = polygonShape;

    body.createFixture(fixtureDef);
    body.setTransform(wallState.getPosition().x, wallState.getPosition().y, wallState.getRotation() * MathUtils.degreesToRadians);

    return body;
  }
}
