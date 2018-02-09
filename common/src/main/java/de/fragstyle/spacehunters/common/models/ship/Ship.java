package de.fragstyle.spacehunters.common.models.ship;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import de.fragstyle.spacehunters.common.drawing.Entity;
import java.util.UUID;

public class Ship extends Entity {

  private static final String ID = "ship";

  public Ship(UUID uuid, World world) {
    super(uuid, world);

    loadBody(world);
  }

  private void loadBody(World world) {
    BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("ship.json"));

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    Body body = world.createBody(bodyDef);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.density = 1;
    fixtureDef.friction = 0.5f;
    fixtureDef.restitution = 0.3f;

    loader.attachFixture(body, ID, fixtureDef, 1);

    this.setBody(body);
    this.setOrigin(loader.getOrigin(ID, 1));
  }
}
